package test;

import org.junit.Assert;
import org.junit.Test;
import org.kevoree.modeling.blas.NetlibBlas;
import org.kevoree.modeling.util.maths.matrix.CommonOps;
import org.kevoree.modeling.util.maths.matrix.DenseMatrix64F;
import org.kevoree.modeling.util.maths.matrix.SimpleMatrix;
import org.kevoree.modeling.util.maths.matrix.solvers.LUDecompositionAlt_D64;
import org.kevoree.modeling.util.maths.structure.KArray2D;
import org.kevoree.modeling.util.maths.structure.blas.KBlas;
import org.kevoree.modeling.util.maths.structure.blas.impl.JavaBlas;
import org.kevoree.modeling.util.maths.structure.impl.NativeArray2D;
import org.kevoree.modeling.util.maths.structure.matrix.solver.LU;
import org.kevoree.modeling.util.maths.structure.matrix.MatrixOperations;

/**
 * Created by assaad on 02/09/15.
 */
public class LUTest {
    public static int r=100;
    public static KBlas java = new JavaBlas();
    public static KBlas netlib = new NetlibBlas();

    @Test
    public void testLUFactorize(){
        int[] dimA = {r, r};

        boolean rand=true;
        double eps=1e-7;

        NativeArray2D matA = new NativeArray2D(dimA[0], dimA[1]);
        MatrixOperations.initMatrice(matA, rand);




        DenseMatrix64F ejmlmatA = new DenseMatrix64F(dimA[0],dimA[1]);
        CommonOps.copyMatrixDense(matA, ejmlmatA);



        LU dlu = new LU(dimA[0],dimA[1]);

        System.out.println("Init done");

        long timestart,timeend;

        timestart=System.currentTimeMillis();
        dlu.factor(matA, netlib);
        KArray2D res= dlu.getLU();
        timeend=System.currentTimeMillis();
        System.out.println("Netlib Factorizarion " + ((double) (timeend - timestart)) / 1000+" s");



        LU dlu2 = new LU(dimA[0],dimA[1]);

        timestart=System.currentTimeMillis();
        dlu2.factor(matA, java);
        KArray2D res2= dlu.getLU();
        timeend=System.currentTimeMillis();
        System.out.println("Java Factorizarion " + ((double) (timeend - timestart)) / 1000+" s");




        LUDecompositionAlt_D64 ludec = new LUDecompositionAlt_D64();
        timestart=System.currentTimeMillis();
        ludec.decompose(ejmlmatA);
        DenseMatrix64F luejml = ludec.getLU();
        timeend=System.currentTimeMillis();
        System.out.println("EJML Factorizarion " + ((double) (timeend - timestart)) / 1000+" s");


        assert res != null;
        assert res2 != null;
        for (int i = 0; i < matA.rows(); i++) {
            for (int j = 0; j < matA.columns(); j++) {
                Assert.assertEquals(res2.get(i, j), res.get(i, j), eps);
                //Assert.assertEquals(luejml.get(i, j), res2.get(i, j), eps);
            }
        }

        System.out.println("done");
    }


}
