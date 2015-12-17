package test;

import org.junit.Assert;
import org.junit.Test;
import org.kevoree.modeling.blas.JCudaBlas;
import org.kevoree.modeling.blas.NetlibBlas;
import org.kevoree.modeling.util.maths.structure.KArray2D;
import org.kevoree.modeling.util.maths.structure.blas.KBlas;
import org.kevoree.modeling.util.maths.structure.blas.KBlasTransposeType;
import org.kevoree.modeling.util.maths.structure.blas.impl.JavaBlas;
import org.kevoree.modeling.util.maths.structure.matrix.MatrixOperations;

/**
 * Created by assaad on 04/09/15.
 */
public class SolverTest {
    @Test
    public void solve() {
        double eps = 1e-7;
        int dim = 200;
        int dim2 = 100;
        KArray2D matA = MatrixOperations.random(dim, dim);
        KArray2D matB = MatrixOperations.random(dim, dim2);
        long timestart, timeend;
        KBlas netblas = new NetlibBlas();
        KBlas javaBlas = new JavaBlas();
        javaBlas.connect();
        netblas.connect();
        //  KBlas jcuda = new JCudaBlas();

        timestart = System.currentTimeMillis();
        KArray2D matXnetlib = MatrixOperations.solve(matA, matB, false, KBlasTransposeType.NOTRANSPOSE, netblas);
        timeend = System.currentTimeMillis();
        System.out.println("Netlib solving " + ((double) (timeend - timestart)) / 1000 + " s");

        timestart = System.currentTimeMillis();
        KArray2D matXjava = MatrixOperations.solve(matA, matB, false, KBlasTransposeType.NOTRANSPOSE, javaBlas);
        timeend = System.currentTimeMillis();
        System.out.println("Java solving " + ((double) (timeend - timestart)) / 1000 + " s");

    /*    timestart=System.currentTimeMillis();
        KArray2D matXCuda=MatrixOperations.solve(matA,matB,false, KBlasTransposeType.NOTRANSPOSE,jcuda);
        timeend=System.currentTimeMillis();
        System.out.println("Cuda invert " + ((double) (timeend - timestart)) / 1000+" s");*/


        KArray2D matCnetlib = MatrixOperations.multiply(matA, matXnetlib, netblas);
        KArray2D matCjava = MatrixOperations.multiply(matA, matXjava, javaBlas);
        //   KArray2D matCcuda =  MatrixOperations.multiply(matA,matXCuda,jcuda);


        double errJava = MatrixOperations.compareMatrix(matCjava, matB);
        double errNetlib = MatrixOperations.compareMatrix(matCnetlib, matB);

        Assert.assertTrue(errJava < eps);
        Assert.assertTrue(errNetlib < eps);

        System.out.println("Error in Java: " + errJava);
        System.out.println("Error in Netlib: " + errNetlib);
        javaBlas.disconnect();
        netblas.disconnect();

    }
}
