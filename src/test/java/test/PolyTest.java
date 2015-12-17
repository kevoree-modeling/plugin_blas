package test;

import org.junit.Assert;
import org.junit.Test;
import org.kevoree.modeling.blas.NetlibBlas;
import org.kevoree.modeling.util.maths.structure.matrix.PolynomialFitBlas;
import org.kevoree.modeling.util.maths.structure.blas.KBlas;
import org.kevoree.modeling.util.maths.structure.blas.impl.JavaBlas;

/**
 * Created by assaad on 16/12/15.
 */
public class PolyTest {
    @Test
    public void polytest() {


        double eps=1e-7;
        double[] coef={5,-4,1,7};
        double[] t={0,1,2,3,4,5,6,7,8,9};
        double[] res=new double[t.length];
        KBlas java = new JavaBlas();
        KBlas netlib = new NetlibBlas();
        java.connect();
        netlib.connect();

        for(int i=0;i<t.length;i++){
            res[i]=PolynomialFitBlas.extrapolate(t[i],coef);
        }




        PolynomialFitBlas pfJava = new PolynomialFitBlas(coef.length-1,java);
        pfJava.fit(t,res);
        double[] javaCoef=pfJava.getCoef();

        for(int i=0;i<coef.length;i++){
            Assert.assertTrue(Math.abs(javaCoef[i]-coef[i])< eps);
        }




        PolynomialFitBlas pfNetlib = new PolynomialFitBlas(coef.length - 1, netlib);
        pfNetlib.fit(t, res);
        double[] blasCoef = pfNetlib.getCoef();

        for (int i = 0; i < coef.length; i++) {
            Assert.assertEquals(blasCoef[i], coef[i], eps);
        }

        java.disconnect();
        netlib.disconnect();


    }

}
