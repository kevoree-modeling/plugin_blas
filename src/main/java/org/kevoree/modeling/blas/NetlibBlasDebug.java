package org.kevoree.modeling.blas;

import com.github.fommil.netlib.BLAS;
import com.github.fommil.netlib.LAPACK;
import org.kevoree.modeling.util.PrimitiveHelper;
import org.kevoree.modeling.util.maths.structure.blas.*;
import org.netlib.blas.Dcopy;
import org.netlib.lapack.Lsame;
import org.netlib.util.intW;

public class NetlibBlasDebug implements KBlas {
    private static BLAS blas;
    private static LAPACK lapack;
    private static BLAS blasJava;
    private static LAPACK lapackJava;

    public NetlibBlasDebug(){
        try{
            blas=BLAS.getInstance();
            lapack=LAPACK.getInstance();
            blasJava=(BLAS)load("com.github.fommil.netlib.F2jBLAS");
            lapackJava=(LAPACK)load("com.github.fommil.netlib.F2jLAPACK");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }


    @Override
    public void dgemm(KBlasTransposeType paramString1, KBlasTransposeType paramString2, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double[] paramArrayOfDouble1, int paramInt4, int paramInt5, double[] paramArrayOfDouble2, int paramInt6, int paramInt7, double paramDouble2, double[] paramArrayOfDouble3, int paramInt8, int paramInt9) {
        blas.dgemm(transTypeToChar(paramString1),transTypeToChar(paramString2), paramInt1,  paramInt2,  paramInt3,  paramDouble1, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble2, paramInt6, paramInt7, paramDouble2, paramArrayOfDouble3, paramInt8, paramInt9);
    }

    @Override
    public void dgetrs(KBlasTransposeType paramString, int paramInt1, int paramInt2, double[] paramArrayOfDouble1, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5, double[] paramArrayOfDouble2, int paramInt6, int paramInt7, int[] paramintW) {
        intW newint = new intW(paramintW[0]);
        lapack.dgetrs(transTypeToChar(paramString), paramInt1, paramInt2, paramArrayOfDouble1, paramInt3, paramInt4, paramArrayOfInt, paramInt5, paramArrayOfDouble2, paramInt6, paramInt7, newint);
        paramintW[0] = newint.val;
    }

    @Override
    public void dgetri(int paramInt1, double[] paramArrayOfDouble1, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4, double[] paramArrayOfDouble2, int paramInt5, int paramInt6, int[] paramintW) {
        intW newint = new intW(paramintW[0]);
        lapack.dgetri(paramInt1, paramArrayOfDouble1,paramInt2, paramInt3, paramArrayOfInt, paramInt4, paramArrayOfDouble2, paramInt5, paramInt6, newint);
        paramintW[0] = newint.val;

    }


    @Override
    public void dgetrf(int paramInt1, int paramInt2, double[] paramArrayOfDouble, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5, int[] paramintW) {
        //intW newint = new intW(paramintW[0]);
        jdgetrf(paramInt1, paramInt2, paramArrayOfDouble, paramInt3, paramInt4, paramArrayOfInt, paramInt5, paramintW);
       // paramintW[0] = newint.val;
    }

    @Override
    public void dorgqr(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble1, int paramInt4, int paramInt5, double[] paramArrayOfDouble2, int paramInt6, double[] paramArrayOfDouble3, int paramInt7, int paramInt8, int[] paramintW) {
        intW newint = new intW(paramintW[0]);
        //jdorgqr(paramInt1, paramInt2, paramInt3, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble2, paramInt6, paramArrayOfDouble3, paramInt7, paramInt8, paramintW);
        lapack.dorgqr(paramInt1, paramInt2, paramInt3, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble2, paramInt6, paramArrayOfDouble3, paramInt7, paramInt8, newint);
        paramintW[0]=newint.val;
    }

    @Override
    public void dgeqrf(int paramInt1, int paramInt2, double[] paramArrayOfDouble1, int paramInt3, int paramInt4, double[] paramArrayOfDouble2, int paramInt5, double[] paramArrayOfDouble3, int paramInt6, int paramInt7, int[] paramintW) {
        //intW newint = new intW(paramintW[0]);
        //lapack.dgeqrf(paramInt1, paramInt2, paramArrayOfDouble1, paramInt3, paramInt4, paramArrayOfDouble2, paramInt5, paramArrayOfDouble3, paramInt6, paramInt7, newint);
        jdgeqrf(paramInt1, paramInt2, paramArrayOfDouble1, paramInt3, paramInt4, paramArrayOfDouble2, paramInt5, paramArrayOfDouble3, paramInt6, paramInt7, paramintW);

        //paramintW[0]=newint.val;
    }

    @Override
    public void shutdown() {

    }


    private static Object load(String className) throws Exception {
        Class klass = Class.forName(className);
        return klass.newInstance();
    }

    
    public void jdgemm(KBlasTransposeType paramString1, KBlasTransposeType paramString2, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double[] paramArrayOfDouble1, int paramInt4, int paramInt5, double[] paramArrayOfDouble2, int paramInt6, int paramInt7, double paramDouble2, double[] paramArrayOfDouble3, int paramInt8, int paramInt9) {

        double d = 0.0D;
        int i = 0;
        //int j = 0;
        int k = 0;
        int m = 0;
        //int n = 0;
        //int i1 = 0;
        //int i2 = 0;
        boolean bool1 = false;
        boolean bool2 = false;
        bool1 = paramString1.equals(KBlasTransposeType.NOTRANSPOSE);
        bool2 = paramString2.equals(KBlasTransposeType.NOTRANSPOSE);
      /*  if (bool1) {
            i1 = paramInt1;
            n = paramInt3;
        } else {
            i1 = paramInt3;
            n = paramInt1;
        }
        if (bool2) {
            i2 = paramInt3;
        } else {
            i2 = paramInt2;
        }
        j = 0;
        if ((((!bool1)) && ((!paramString1.equals(KBlasTransposeType.CONJUGATE))) ? 1 : 0) != 0) {
        }
        if (((!paramString1.equals(KBlasTransposeType.TRANSPOSE)) ? 1 : 0) != 0) {
            j = 1;
        } else {
            if ((((!bool2)) && ((!paramString2.equals(KBlasTransposeType.CONJUGATE))) ? 1 : 0) != 0) {
            }
            if (((!paramString2.equals(KBlasTransposeType.TRANSPOSE)) ? 1 : 0) != 0) {
                j = 2;
            } else if ((paramInt1 <0)) {
                j = 3;
            } else if ((paramInt2 <0)) {
                j = 4;
            } else if ((paramInt3 <0)) {
                j = 5;
            } else if ((paramInt5 >= Math.max(1, i1) ^ true)) {
                j = 8;
            } else if ((paramInt7 >= Math.max(1, i2) ^ true)) {
                j = 10;
            } else if ((paramInt9 >= Math.max(1, paramInt1) ^ true)) {
                j = 13;
            }
        }*/
        /*if ((j !=0)) {
            // //Xerbla.xerbla("DGEMM ", j);
            return;
        }*/
       /* if ((paramInt1 != 0 ? 0 : 1) == 0) {}
        if (((paramInt2 != 0 ? 0 : 1) == 0 ? 0 : 1) == 0)
        {
            if ((paramDouble1 != 0.0D ? 0 : 1) == 0) {}
            if (((paramInt3 != 0 ? 0 : 1) == 0 ? 0 : 1) == 0) {}
        }
        if ((((paramDouble2 != 1.0D ^ true) ? 1 : 0) !=0)) {
            return;
        }*/
        int i3;
        int i4;
        if ((paramDouble1 ==0)) {
            if ((paramDouble2 ==0)) {
                k = 1;
                for (i3 = paramInt2; i3 > 0; i3--) {
                    i = 1;
                    for (i4 = paramInt1; i4 > 0; i4--) {
                        paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)] = 0.0D;
                        i++;
                    }
                    k++;
                }
            } else {
                k = 1;
                for (i3 = paramInt2; i3 > 0; i3--) {
                    i = 1;
                    for (i4 = paramInt1; i4 > 0; i4--) {
                        paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)] = (paramDouble2 * paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)]);
                        i++;
                    }
                    k++;
                }
            }
            return;
        }
        int i5;
        if (bool2) {
            if (bool1) {
                k = 1;
                for (i3 = paramInt2; i3 > 0; i3--) {
                    if ((paramDouble2 ==0)) {
                        i = 1;
                        for (i4 = paramInt1; i4 > 0; i4--) {
                            paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)] = 0.0D;
                            i++;
                        }
                    } else {
                        i = 1;
                        i4 = paramInt1;
                        for (; ; ) {
                            paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)] = (paramDouble2 * paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)]);
                            i++;
                            i4--;
                            if (i4 <= 0) {
                                break;
                            }
                        }
                    }
                    m = 1;
                    for (i4 = paramInt3; i4 > 0; i4--) {
                        if ((paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt7 + paramInt6)] !=0.0D)) {
                            d = paramDouble1 * paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt7 + paramInt6)];
                            i = 1;
                            for (i5 = paramInt1; i5 > 0; i5--) {
                                paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)] += d * paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt5 + paramInt4)];
                                i++;
                            }
                        }
                        m++;
                    }
                    k++;
                }
            } else {
                k = 1;
                for (i3 = paramInt2; i3 > 0; i3--) {
                    i = 1;
                    for (i4 = paramInt1; i4 > 0; i4--) {
                        d = 0.0D;
                        m = 1;
                        for (i5 = paramInt3; i5 > 0; i5--) {
                            d += paramArrayOfDouble1[(m - 1 + (i - 1) * paramInt5 + paramInt4)] * paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt7 + paramInt6)];
                            m++;
                        }
                        if ((paramDouble2 ==0)) {
                            paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)] = (paramDouble1 * d);
                        } else {
                            paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)] = (paramDouble1 * d + paramDouble2 * paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)]);
                        }
                        i++;
                    }
                    k++;
                }
            }
        } else if (bool1) {
            k = 1;
            for (i3 = paramInt2; i3 > 0; i3--) {
                if ((paramDouble2 ==0)) {
                    i = 1;
                    for (i4 = paramInt1; i4 > 0; i4--) {
                        paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)] = 0.0D;
                        i++;
                    }
                } else if ((paramDouble2 !=1.0D)) {
                    i = 1;
                    i4 = paramInt1;
                    for (; ; ) {
                        paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)] = (paramDouble2 * paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)]);
                        i++;
                        i4--;
                        if (i4 <= 0) {
                            break;
                        }
                    }
                }
                m = 1;
                for (i4 = paramInt3; i4 > 0; i4--) {
                    if ((paramArrayOfDouble2[(k - 1 + (m - 1) * paramInt7 + paramInt6)] !=0.0D)) {
                        d = paramDouble1 * paramArrayOfDouble2[(k - 1 + (m - 1) * paramInt7 + paramInt6)];
                        i = 1;
                        for (i5 = paramInt1; i5 > 0; i5--) {
                            paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)] += d * paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt5 + paramInt4)];
                            i++;
                        }
                    }
                    m++;
                }
                k++;
            }
        } else {
            k = 1;
            for (i3 = paramInt2; i3 > 0; i3--) {
                i = 1;
                for (i4 = paramInt1; i4 > 0; i4--) {
                    d = 0.0D;
                    m = 1;
                    for (i5 = paramInt3; i5 > 0; i5--) {
                        d += paramArrayOfDouble1[(m - 1 + (i - 1) * paramInt5 + paramInt4)] * paramArrayOfDouble2[(k - 1 + (m - 1) * paramInt7 + paramInt6)];
                        m++;
                    }
                    if ((paramDouble2 ==0)) {
                        paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)] = (paramDouble1 * d);
                    } else {
                        paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)] = (paramDouble1 * d + paramDouble2 * paramArrayOfDouble3[(i - 1 + (k - 1) * paramInt9 + paramInt8)]);
                    }
                    i++;
                }
                k++;
            }
        }
    }


    
    public void jdgetri(int paramInt1, double[] paramArrayOfDouble1, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4, double[] paramArrayOfDouble2, int paramInt5, int paramInt6, int[] paramintW)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        int n = 0;
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        paramintW[0] = 0;
        i5 = ilaenv(1, "DGETRI", " ", paramInt1, -1, -1, -1); //should be 64
        i4 = paramInt1 * i5;
        paramArrayOfDouble2[paramInt5] = i4;
        i = paramInt6 != -1 ? 0 : 1;
        if ((paramInt1 <0))
        {
            paramintW[0] = -1;
        }
        else if ((paramInt3 < Math.max(1, paramInt1)))
        {
            paramintW[0] = -3;
        }

        if ((paramintW[0] !=0))
        {
            //Xerbla.xerbla("DGETRI", -paramintW[0]);
            return;
        }
        if (i != 0) {
            return;
        }
        if ((paramInt1 == 0)) {
            return;
        }
        jdtrtri(KBlasOrientationType.UPPER, KBlasUnitType.NONUNIT, paramInt1, paramArrayOfDouble1, paramInt2, paramInt3, paramintW);
        if ((paramintW[0] >0)) {
            return;
        }
        i6 = 2;
        i3 = paramInt1;
        if (((i5 < paramInt1) ? 1 : 0) != 0)
        {
            k = Math.max(i3 * i5, 1);
            if ((paramInt6 < k))
            {
                i5 = floorDiv(paramInt6, i3);
                i6 = Math.max(2, ilaenv(2, "DGETRI", " ", paramInt1, -1, -1, -1));
            }
        }
        else
        {
            k = paramInt1;
        }
        int i9;
        if (((i5 >= paramInt1)))
        {
            m = paramInt1;
            for (int i8 = paramInt1 ; i8 > 0; i8--)
            {
                j = m + 1;
                for (i9 = paramInt1 - (m + 1) + 1; i9 > 0; i9--)
                {
                    paramArrayOfDouble2[(j - 1 + paramInt5)] = paramArrayOfDouble1[(j - 1 + (m - 1) * paramInt3 + paramInt2)];
                    paramArrayOfDouble1[(j - 1 + (m - 1) * paramInt3 + paramInt2)] = 0.0D;
                    j++;
                }
                if ((m < paramInt1)) {
                    jdgemv(KBlasTransposeType.NOTRANSPOSE, paramInt1, paramInt1 - m, -1.0D, paramArrayOfDouble1, (m ) * paramInt3 + paramInt2, paramInt3, paramArrayOfDouble2, m + paramInt5, 1, 1.0D, paramArrayOfDouble1, (m - 1) * paramInt3 + paramInt2, 1);
                }
                m += -1;
            }
        }
        else
        {
            i7 = floorDiv((paramInt1 - 1), i5) * i5 + 1;
            m = i7;
            for (int i8 = floorDiv((1 - i7 + -i5), -i5); i8 > 0; i8--)
            {
                n = Math.min(i5, paramInt1 - m + 1);
                i1 = m;
                for (i9 = n; i9 > 0; i9--)
                {
                    j = i1 + 1;
                    for (int i10 = paramInt1 - (i1 + 1) + 1; i10 > 0; i10--)
                    {
                        paramArrayOfDouble2[(j + (i1 - m) * i3 - 1 + paramInt5)] = paramArrayOfDouble1[(j - 1 + (i1 - 1) * paramInt3 + paramInt2)];
                        paramArrayOfDouble1[(j - 1 + (i1 - 1) * paramInt3 + paramInt2)] = 0.0D;
                        j++;
                    }
                    i1++;
                }
                if ((m + n <= paramInt1)) {
                    dgemm(KBlasTransposeType.NOTRANSPOSE, KBlasTransposeType.NOTRANSPOSE, paramInt1, n, paramInt1 - m - n + 1, -1.0D, paramArrayOfDouble1, (m + n - 1) * paramInt3 + paramInt2, paramInt3, paramArrayOfDouble2, m + n - 1 + paramInt5, i3, 1.0D, paramArrayOfDouble1, (m - 1) * paramInt3 + paramInt2, paramInt3);
                }
                jdtrsm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.UNIT, paramInt1, n, 1.0D, paramArrayOfDouble2, m - 1 + paramInt5, i3, paramArrayOfDouble1, (m - 1) * paramInt3 + paramInt2, paramInt3);
                m += -i5;
            }
        }
        m = paramInt1 - 1;
        for (int i8 = -1 + paramInt1; i8 > 0; i8--)
        {
            i2 = paramArrayOfInt[(m - 1 + paramInt4)];
            if ((i2 != m)) {
                jdswap(paramInt1, paramArrayOfDouble1, (m - 1) * paramInt3 + paramInt2, 1, paramArrayOfDouble1, (i2 - 1) * paramInt3 + paramInt2, 1);
            }
            m += -1;
        }
        paramArrayOfDouble2[(paramInt5)] = k;
    }

    
    public void jdgetrf(int paramInt1, int paramInt2, double[] paramArrayOfDouble, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5, int[] info) {
        int i = 0;
        info[0] = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        info[0] = 0;
        int[] localintW = new int[1];
        localintW[0] = 0;

        if ((paramInt1 <0)) {
            info[0] = -1;
        } else if ((paramInt2 <0)) {
            info[0] = -2;
        } else if ((paramInt4 < Math.max(1, paramInt1))) {
            info[0] = -4;
        }
        if ((info[0] !=0)) {
            //Xerbla.xerbla("DGETRF", -info[0]); //todo check error
            return;
        }

        if (((paramInt2 == 0))) {
            return;
        }
        m = ilaenv(1, "DGETRF", " ", paramInt1, paramInt2, -1, -1);
        if (((m >= Math.min(paramInt1, paramInt2)))) {
            jdgetf2(paramInt1, paramInt2, paramArrayOfDouble, paramInt3, paramInt4, paramArrayOfInt, paramInt5, info);
        } else {
            j = 1;
            for (int n = floorDiv((Math.min(paramInt1, paramInt2) - 1 + m), m); n > 0; n--) {
                k = Math.min(Math.min(paramInt1, paramInt2) - j + 1, m);
                jdgetf2(paramInt1 - j + 1, k, paramArrayOfDouble, j - 1 + (j - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfInt, j - 1 + paramInt5, localintW);
                i = j;
                for (int i1 = Math.min(paramInt1, j + k - 1) - j + 1; i1 > 0; i1--) {
                    paramArrayOfInt[(i - 1 + paramInt5)] = (j - 1 + paramArrayOfInt[(i - 1 + paramInt5)]);
                    i++;
                }
                jdlaswp(j - 1, paramArrayOfDouble, paramInt3, paramInt4, j, j + k - 1, paramArrayOfInt, paramInt5, 1);
                if ((j + k <= paramInt2)) {
                    jdlaswp(paramInt2 - j - k + 1, paramArrayOfDouble, (j + k - 1) * paramInt4 + paramInt3, paramInt4, j, j + k - 1, paramArrayOfInt, paramInt5, 1);
                    jdtrsm(KBlasSideType.LEFT, KBlasOrientationType.LOWER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.UNIT, k, paramInt2 - j - k + 1, 1.0D, paramArrayOfDouble, j - 1 + (j - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble, j - 1 + (j + k - 1) * paramInt4 + paramInt3, paramInt4);
                    if ((j + k <= paramInt1)) {
                        dgemm(KBlasTransposeType.NOTRANSPOSE, KBlasTransposeType.NOTRANSPOSE, paramInt1 - j - k + 1, paramInt2 - j - k + 1, k, -1.0D, paramArrayOfDouble, j + k - 1 + (j - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble, j - 1 + (j + k - 1) * paramInt4 + paramInt3, paramInt4, 1.0D, paramArrayOfDouble, j + k - 1 + (j + k - 1) * paramInt4 + paramInt3, paramInt4);
                    }
                }
                j += m;
            }
        }
    }

    
    public void jdgetrs(KBlasTransposeType trans, int nOrder, int nrhs, double[] matA, int offsetA, int ldA, int[] ipiv, int offsetIpiV, double[] matB, int offsetB, int ldB, int[] info) {
        boolean bool = false;
        info[0] = 0;
        bool = trans.equals(KBlasTransposeType.NOTRANSPOSE);

  /*      if (((!trans.equals(KBlasTransposeType.CONJUGATE)) ? 1 : 0) != 0) {
            info[0] = -1;
        } else if ((nOrder <0)) {
            info[0] = -2;
        } else if ((nrhs <0)) {
            info[0] = -3;
        } else if ((ldA < Math.max(1, nOrder))) {
            info[0] = -5;
        } else if ((ldB < Math.max(1, nOrder))) {
            info[0] = -8;
        }
        if ((info[0] !=0)) {
            //Xerbla.xerbla("DGETRS", -info[0]);
            return;
        }

            if (((nrhs == 0))) {
                return;
            }*/
        if (bool) {
            jdlaswp(nrhs, matB, offsetB, ldB, 1, nOrder, ipiv, offsetIpiV, 1);
            jdtrsm(KBlasSideType.LEFT, KBlasOrientationType.LOWER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.UNIT, nOrder, nrhs, 1.0D, matA, offsetA, ldA, matB, offsetB, ldB);
            jdtrsm(KBlasSideType.LEFT, KBlasOrientationType.UPPER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.NONUNIT, nOrder, nrhs, 1.0D, matA, offsetA, ldA, matB, offsetB, ldB);
        } else {
            jdtrsm(KBlasSideType.LEFT, KBlasOrientationType.UPPER, KBlasTransposeType.TRANSPOSE, KBlasUnitType.NONUNIT, nOrder, nrhs, 1.0D, matA, offsetA, ldA, matB, offsetB, ldB);
            jdtrsm(KBlasSideType.LEFT, KBlasOrientationType.LOWER, KBlasTransposeType.TRANSPOSE, KBlasUnitType.UNIT, nOrder, nrhs, 1.0D, matA, offsetA, ldA, matB, offsetB, ldB);
            jdlaswp(nrhs, matB, offsetB, ldB, 1, nOrder, ipiv, offsetIpiV, -1);
        }
    }

    
    public void jdorgqr(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble1, int paramInt4, int paramInt5, double[] paramArrayOfDouble2, int paramInt6, double[] paramArrayOfDouble3, int paramInt7, int paramInt8, int[] paramintW)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        int[] localintW = new int[1];
        int m = 0;
        int n = 0;
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        paramintW[0] = 0;
        i6 = ilaenv(1, "DORGQR", " ", paramInt1, paramInt2, paramInt3, -1);
        i5 = Math.max(1, paramInt2) * i6;
        paramArrayOfDouble3[(paramInt7)] = i5;
        i = paramInt8 != -1 ? 0 : 1;
        if ((paramInt1 >= 0 ? 0 : 1) != 0)
        {
            paramintW[0] = -1;
        }
        else
        {
            //if ((paramInt2 >= 0 ? 0 : 1) == 0) {}
            if (((paramInt2 <= paramInt1 ? 0 : 1) == 0 ? 0 : 1) != 0)
            {
                paramintW[0] = -2;
            }
            else
            {
                if (((paramInt3 <= paramInt2 ? 0 : 1) == 0 ? 0 : 1) != 0)
                {
                    paramintW[0] = -3;
                }
                else if ((paramInt5 >= Math.max(1, paramInt1) ? 0 : 1) != 0)
                {
                    paramintW[0] = -5;
                }
               /* else
                {
                    if (((i ^ 0x1) != 0 ? 1 : 0) != 0) {
                        paramintW[0] = -8;
                    }
                }*/
            }
        }
        if ((paramintW[0] == 0 ? 0 : 1) != 0)
        {
            //Xerbla.xerbla("DORGQR", -paramintW[0]);
            return;
        }
        if (i != 0) {
            return;
        }
        if ((paramInt2 > 0 ? 0 : 1) != 0)
        {
            paramArrayOfDouble3[(paramInt7)] = 1;
            return;
        }
        i7 = 2;
        i8 = 0;
        m = paramInt2;
        if (((i6 >= paramInt3 ? 0 : 1) != 0 ? 1 : 0) != 0)
        {
            i8 = Math.max(0, ilaenv(3, "DORGQR", " ", paramInt1, paramInt2, paramInt3, -1));
            if ((i8 >= paramInt3 ? 0 : 1) != 0)
            {
                i4 = paramInt2;
                m = i4 * i6;
                if ((paramInt8 >= m ? 0 : 1) != 0)
                {
                    i6 = paramInt8 / i4;
                    i7 = Math.max(2, ilaenv(2, "DORGQR", " ", paramInt1, paramInt2, paramInt3, -1));
                }
            }
        }
        int i9;
        int i10;
        if (((i8 >= paramInt3 ? 0 : 1) != 0 ? 1 : 0) != 0)
        {
            i1 = (paramInt3 - i8 - 1) / i6 * i6;
            i2 = Math.min(paramInt3, i1 + i6);
            n = i2 + 1;
            for (i9 = paramInt2 - (i2 + 1) + 1; i9 > 0; i9--)
            {
                j = 1;
                for (i10 = i2; i10 > 0; i10--)
                {
                    paramArrayOfDouble1[(j - 1 + (n - 1) * paramInt5 + paramInt4)] = 0.0D;
                    j += 1;
                }
                n += 1;
            }
        }
        else
        {
            i2 = 0;
        }
        if ((i2 >= paramInt2 ? 0 : 1) != 0) {
            jdorg2r(paramInt1 - i2, paramInt2 - i2, paramInt3 - i2, paramArrayOfDouble1, i2+ (i2 ) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble2, i2+ paramInt6, paramArrayOfDouble3, paramInt7, localintW);
        }
        if ((i2 <= 0 ? 0 : 1) != 0)
        {
            j = i1 + 1;
            for (i9 = (1 - (i1 + 1) + -i6) / -i6; i9 > 0; i9--)
            {
                k = Math.min(i6, paramInt3 - j + 1);
                if ((j + k > paramInt2 ? 0 : 1) != 0)
                {
                    jdlarft(KBlasDirectionType.FORWARD, KBlasMajorType.COLUMNWISE, paramInt1 - j + 1, k, paramArrayOfDouble1, j - 1 + (j - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble2, j - 1 + paramInt6, paramArrayOfDouble3, paramInt7, i4);
                    jdlarfb(KBlasSideType.LEFT, KBlasTransposeType.NOTRANSPOSE, KBlasDirectionType.FORWARD, KBlasMajorType.COLUMNWISE, paramInt1 - j + 1, paramInt2 - j - k + 1, k, paramArrayOfDouble1, j - 1 + (j - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble3, paramInt7, i4, paramArrayOfDouble1, j - 1 + (j + k - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble3, k+ paramInt7, i4);
                }
                jdorg2r(paramInt1 - j + 1, k, k, paramArrayOfDouble1, j - 1 + (j - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble2, j - 1 + paramInt6, paramArrayOfDouble3, paramInt7, localintW);
                n = j;
                for (i10 = j + k - 1 - j + 1; i10 > 0; i10--)
                {
                    i3 = 1;
                    for (int i11 = j - 1; i11 > 0; i11--)
                    {
                        paramArrayOfDouble1[(i3 - 1 + (n - 1) * paramInt5 + paramInt4)] = 0.0D;
                        i3 += 1;
                    }
                    n += 1;
                }
                j += -i6;
            }
        }
        paramArrayOfDouble3[(paramInt7)] = m;
    }

    
    public void jdgeqrf(int paramInt1, int paramInt2, double[] paramArrayOfDouble1, int paramInt3, int paramInt4, double[] paramArrayOfDouble2, int paramInt5, double[] paramArrayOfDouble3, int paramInt6, int paramInt7, int[] paramintW)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        int[] localintW=new int[1];
        int m = 0;
        int n = 0;
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        paramintW[0] = 0;
        i3 = ilaenv(1, "DGEQRF", " ", paramInt1, paramInt2, -1, -1); //32 for this
        i2 = paramInt2 * i3;
        paramArrayOfDouble3[ paramInt6] = i2;
        i = paramInt7 != -1 ? 0 : 1;
        if ((paramInt1 >= 0 ? 0 : 1) != 0)
        {
            paramintW[0] = -1;
        }
        else if ((paramInt2 >= 0 ? 0 : 1) != 0)
        {
            paramintW[0] = -2;
        }
        else if ((paramInt4 >= Math.max(1, paramInt1) ? 0 : 1) != 0)
        {
            paramintW[0] = -4;
        }
        /*else
        {
            if ((i != 0 ? 1 : 0) != 0) {
                paramintW[0] = -7;
            }
        }*/
        if ((paramintW[0] == 0 ? 0 : 1) != 0)
        {
            //  //Xerbla.xerbla("DGEQRF", -paramintW[0]);
            return;
        }
        if (i != 0) {
            return;
        }
        n = Math.min(paramInt1, paramInt2);
        if ((n != 0 ? 0 : 1) != 0)
        {
            paramArrayOfDouble3[paramInt6] = 1;
            return;
        }
        i4 = 2;
        i5 = 0;
        m = paramInt2;
        if (((i3 >= n ? 0 : 1) != 0 ? 1 : 0) != 0)
        {
            i5 = Math.max(0, ilaenv(3, "DGEQRF", " ", paramInt1, paramInt2, -1, -1));
            if ((i5 >= n ? 0 : 1) != 0)
            {
                i1 = paramInt2;
                m = i1 * i3;
                if ((paramInt7 >= m ? 0 : 1) != 0)
                {
                    i3 = paramInt7 / i1;
                    i4 = Math.max(2, ilaenv(2, "DGEQRF", " ", paramInt1, paramInt2, -1, -1));
                }
            }
        }
        if (((i5 >= n ? 0 : 1) != 0 ? 1 : 0) != 0)
        {
            j = 1;
            for (int i6 = (n - i5 - 1 + i3) / i3; i6 > 0; i6--)
            {
                k = Math.min(n - j + 1, i3);
                jdgeqr2(paramInt1 - j + 1, k, paramArrayOfDouble1, j - 1 + (j - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble2, j - 1 + paramInt5, paramArrayOfDouble3, paramInt6, localintW);
                if ((j + k > paramInt2 ? 0 : 1) != 0)
                {
                    //lapack.dlarft("Forward", "Columnwise", paramInt1 - j + 1, k, paramArrayOfDouble1, j - 1 + (j - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble2, j - 1 + paramInt5, paramArrayOfDouble3, paramInt6, i1);
                     jdlarft(KBlasDirectionType.FORWARD, KBlasMajorType.COLUMNWISE, paramInt1 - j + 1, k, paramArrayOfDouble1, j - 1 + (j - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble2, j - 1 + paramInt5, paramArrayOfDouble3, paramInt6, i1);
                    //lapack.dlarfb("Left", "Transpose", "Forward", "Columnwise", paramInt1 - j + 1, paramInt2 - j - k + 1, k, paramArrayOfDouble1, j - 1 + (j - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble3, paramInt6, i1, paramArrayOfDouble1, j - 1 + (j + k - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble3, k + 1 - 1 + paramInt6, i1);
                  //  jdlarfb(KBlasSideType.LEFT, KBlasTransposeType.TRANSPOSE, KBlasDirectionType.FORWARD, KBlasMajorType.COLUMNWISE, paramInt1 - j + 1, paramInt2 - j - k + 1, k, paramArrayOfDouble1, j - 1 + (j - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble3, paramInt6, i1, paramArrayOfDouble1, j - 1 + (j + k - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble3, k+ paramInt6, i1);
                    jdlarfb("Left", "Transpose", "Forward", "Columnwise", paramInt1 - j + 1, paramInt2 - j - k + 1, k, paramArrayOfDouble1, j - 1 + (j - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble3, paramInt6, i1, paramArrayOfDouble1, j - 1 + (j + k - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble3, k + 1 - 1 + paramInt6, i1);

                }
                j += i3;
            }
        }
        else
        {
            j = 1;
        }
        if ((j > n ? 0 : 1) != 0) {
            jdgeqr2(paramInt1 - j + 1, paramInt2 - j + 1, paramArrayOfDouble1, j - 1 + (j - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble2, j - 1 + paramInt5, paramArrayOfDouble3, paramInt6, localintW);
        }
        paramArrayOfDouble3[(paramInt6)] = m;
    }

    public  void jdorg2r(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble1, int paramInt4, int paramInt5, double[] paramArrayOfDouble2, int paramInt6, double[] paramArrayOfDouble3, int paramInt7, int[] paramintW)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        paramintW[0] = 0;
        if ((paramInt1 >= 0 ? 0 : 1) != 0)
        {
            paramintW[0] = -1;
        }
        else
        {
            if ((paramInt2 >= 0 ? 0 : 1) == 0) {}
            if (((paramInt2 <= paramInt1 ? 0 : 1) == 0 ? 0 : 1) != 0)
            {
                paramintW[0] = -2;
            }
            else
            {
                if ((paramInt3 >= 0 ? 0 : 1) == 0) {}
                if (((paramInt3 <= paramInt2 ? 0 : 1) == 0 ? 0 : 1) != 0) {
                    paramintW[0] = -3;
                } else if ((paramInt5 >= Math.max(1, paramInt1) ? 0 : 1) != 0) {
                    paramintW[0] = -5;
                }
            }
        }
        if ((paramintW[0] == 0 ? 0 : 1) != 0)
        {
            //Xerbla.xerbla("DORG2R", -paramintW[0]);
            return;
        }
        if ((paramInt2 > 0 ? 0 : 1) != 0) {
            return;
        }
        j = paramInt3 + 1;
        int n;
        for (int m = paramInt2 - (paramInt3 + 1) + 1; m > 0; m--)
        {
            k = 1;
            for (n = paramInt1; n > 0; n--)
            {
                paramArrayOfDouble1[(k - 1 + (j - 1) * paramInt5 + paramInt4)] = 0.0D;
                k += 1;
            }
            paramArrayOfDouble1[(j - 1 + (j - 1) * paramInt5 + paramInt4)] = 1.0D;
            j += 1;
        }
        i = paramInt3;
        for (int m = (1 - paramInt3 + -1) / -1; m > 0; m--)
        {
            if ((i >= paramInt2 ? 0 : 1) != 0)
            {
                paramArrayOfDouble1[(i - 1 + (i - 1) * paramInt5 + paramInt4)] = 1.0D;
                jdlarf(KBlasSideType.LEFT, paramInt1 - i + 1, paramInt2 - i, paramArrayOfDouble1, i - 1 + (i - 1) * paramInt5 + paramInt4, 1, paramArrayOfDouble2[(i - 1 + paramInt6)], paramArrayOfDouble1, i - 1 + (i ) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble3, paramInt7);
            }
            if ((i >= paramInt1 ? 0 : 1) != 0) {
                jdscal(paramInt1 - i, -paramArrayOfDouble2[(i - 1 + paramInt6)], paramArrayOfDouble1, i+ (i - 1) * paramInt5 + paramInt4, 1);
            }
            paramArrayOfDouble1[(i - 1 + (i - 1) * paramInt5 + paramInt4)] = (1.0D - paramArrayOfDouble2[(i - 1 + paramInt6)]);
            k = 1;
            for (n = i - 1; n > 0; n--)
            {
                paramArrayOfDouble1[(k - 1 + (i - 1) * paramInt5 + paramInt4)] = 0.0D;
                k += 1;
            }
            i += -1;
        }
    }


  public void jdlarfb(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble1, int paramInt4, int paramInt5, double[] paramArrayOfDouble2, int paramInt6, int paramInt7, double[] paramArrayOfDouble3, int paramInt8, int paramInt9, double[] paramArrayOfDouble4, int paramInt10, int paramInt11)
    {
        KBlasSideType paramString1s = KBlasSideType.LEFT;
        KBlasTransposeType paramString2s = KBlasTransposeType.TRANSPOSE;
        KBlasTransposeType strs=KBlasTransposeType.NOTRANSPOSE;
        KBlasDirectionType paramString3s = KBlasDirectionType.FORWARD;
        KBlasMajorType paramString4s = KBlasMajorType.COLUMNWISE;
        String str = new String(" ");
        int i = 0;
        int j = 0;
        if ((paramInt1 > 0 ? 0 : 1) == 0) {}
        if (((paramInt2 > 0 ? 0 : 1) == 0 ? 0 : 1) != 0) {
            return;
        }
        if (Lsame.lsame(paramString2, "N")) {
            str = "T";
        } else {
            str = "N";
        }
        int k;
        int m;
        if (Lsame.lsame(paramString4, "C"))
        {
            if (Lsame.lsame(paramString3, "F"))
            {
                if (Lsame.lsame(paramString1, "L"))
                {
                    j = 1;
                    for (k = paramInt3; k > 0; k--)
                    {
                        jdcopy(paramInt2, paramArrayOfDouble3, j - 1 + paramInt8, paramInt9, paramArrayOfDouble4, (j - 1) * paramInt11 + paramInt10, 1);
                        j += 1;
                    }
                    //blas.dtrmm("Right", "Lower", "No transpose", "Unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.UNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                        jdgemm(KBlasTransposeType.TRANSPOSE, KBlasTransposeType.NOTRANSPOSE, paramInt2, paramInt3, paramInt1 - paramInt3, 1.0D, paramArrayOfDouble3, paramInt3+ paramInt8, paramInt9, paramArrayOfDouble1, paramInt3 + paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                    }
                    //blas.dtrmm("Right", "Upper", str, "Non-unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, strs, KBlasUnitType.NONUNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                        jdgemm(KBlasTransposeType.NOTRANSPOSE, KBlasTransposeType.TRANSPOSE, paramInt1 - paramInt3, paramInt2, paramInt3, -1.0D, paramArrayOfDouble1, paramInt3+ paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11, 1.0D, paramArrayOfDouble3, paramInt3+ (1 - 1) * paramInt9 + paramInt8, paramInt9);
                    }
                   // blas.dtrmm("Right", "Lower", "Transpose", "Unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, KBlasTransposeType.TRANSPOSE, KBlasUnitType.UNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    j = 1;
                    for (k = paramInt3; k > 0; k--)
                    {
                        i = 1;
                        for (m = paramInt2; m > 0; m--)
                        {
                            paramArrayOfDouble3[(j - 1 + (i - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                            i += 1;
                        }
                        j += 1;
                    }
                    /*
                    j = 1;
                    for (k = paramInt3 - 1 + 1; k > 0; k--)
                    {
                        Dcopy.dcopy(paramInt2, paramArrayOfDouble3, j - 1 + (1 - 1) * paramInt9 + paramInt8, paramInt9, paramArrayOfDouble4, 1 - 1 + (j - 1) * paramInt11 + paramInt10, 1);
                        j += 1;
                    }
                    blas.dtrmm("Right", "Lower", "No transpose", "Unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                        blas.dgemm("Transpose", "No transpose", paramInt2, paramInt3, paramInt1 - paramInt3, 1.0D, paramArrayOfDouble3, paramInt3 + 1 - 1 + (1 - 1) * paramInt9 + paramInt8, paramInt9, paramArrayOfDouble1, paramInt3 + 1 - 1 + (1 - 1) * paramInt5 + paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                    }
                    blas.dtrmm("Right", "Upper", str, "Non-unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                        blas.dgemm("No transpose", "Transpose", paramInt1 - paramInt3, paramInt2, paramInt3, -1.0D, paramArrayOfDouble1, paramInt3 + 1 - 1 + (1 - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11, 1.0D, paramArrayOfDouble3, paramInt3 + 1 - 1 + (1 - 1) * paramInt9 + paramInt8, paramInt9);
                    }
                    blas.dtrmm("Right", "Lower", "Transpose", "Unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    j = 1;
                    for (k = paramInt3 - 1 + 1; k > 0; k--)
                    {
                        i = 1;
                        for (m = paramInt2 - 1 + 1; m > 0; m--)
                        {
                            paramArrayOfDouble3[(j - 1 + (i - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                            i += 1;
                        }
                        j += 1;
                    }
                    */
                }
                else if (Lsame.lsame(paramString1, "R"))
                {
                    j = 1;
                    for (k = paramInt3 - 1 + 1; k > 0; k--)
                    {
                        Dcopy.dcopy(paramInt1, paramArrayOfDouble3, 1 - 1 + (j - 1) * paramInt9 + paramInt8, 1, paramArrayOfDouble4, 1 - 1 + (j - 1) * paramInt11 + paramInt10, 1);
                        j += 1;
                    }
                    blas.dtrmm("Right", "Lower", "No transpose", "Unit", paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                        blas.dgemm("No transpose", "No transpose", paramInt1, paramInt3, paramInt2 - paramInt3, 1.0D, paramArrayOfDouble3, 1 - 1 + (paramInt3 + 1 - 1) * paramInt9 + paramInt8, paramInt9, paramArrayOfDouble1, paramInt3 + 1 - 1 + (1 - 1) * paramInt5 + paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                    }
                    blas.dtrmm("Right", "Upper", paramString2, "Non-unit", paramInt1, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                        blas.dgemm("No transpose", "Transpose", paramInt1, paramInt2 - paramInt3, paramInt3, -1.0D, paramArrayOfDouble4, paramInt10, paramInt11, paramArrayOfDouble1, paramInt3 + 1 - 1 + (1 - 1) * paramInt5 + paramInt4, paramInt5, 1.0D, paramArrayOfDouble3, 1 - 1 + (paramInt3 + 1 - 1) * paramInt9 + paramInt8, paramInt9);
                    }
                    blas.dtrmm("Right", "Lower", "Transpose", "Unit", paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    j = 1;
                    k = paramInt3 - 1 + 1;
                    for (;;)
                    {
                        i = 1;
                        for (m = paramInt1 - 1 + 1; m > 0; m--)
                        {
                            paramArrayOfDouble3[(i - 1 + (j - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                            i += 1;
                        }
                        j += 1;
                        k--;
                        if (k <= 0) {
                            break;
                        }
                    }
                }
            }
            else if (Lsame.lsame(paramString1, "L"))
            {
                j = 1;
                for (k = paramInt3 - 1 + 1; k > 0; k--)
                {
                    Dcopy.dcopy(paramInt2, paramArrayOfDouble3, paramInt1 - paramInt3 + j - 1 + (1 - 1) * paramInt9 + paramInt8, paramInt9, paramArrayOfDouble4, 1 - 1 + (j - 1) * paramInt11 + paramInt10, 1);
                    j += 1;
                }
                blas.dtrmm("Right", "Upper", "No transpose", "Unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt1 - paramInt3 + 1 - 1 + (1 - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                    blas.dgemm("Transpose", "No transpose", paramInt2, paramInt3, paramInt1 - paramInt3, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9, paramArrayOfDouble1, paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                }
                blas.dtrmm("Right", "Lower", str, "Non-unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                    blas.dgemm("No transpose", "Transpose", paramInt1 - paramInt3, paramInt2, paramInt3, -1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9);
                }
                blas.dtrmm("Right", "Upper", "Transpose", "Unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt1 - paramInt3 + 1 - 1 + (1 - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                j = 1;
                for (k = paramInt3 - 1 + 1; k > 0; k--)
                {
                    i = 1;
                    for (m = paramInt2 - 1 + 1; m > 0; m--)
                    {
                        paramArrayOfDouble3[(paramInt1 - paramInt3 + j - 1 + (i - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                        i += 1;
                    }
                    j += 1;
                }
            }
            else if (Lsame.lsame(paramString1, "R"))
            {
                j = 1;
                for (k = paramInt3 - 1 + 1; k > 0; k--)
                {
                    Dcopy.dcopy(paramInt1, paramArrayOfDouble3, 1 - 1 + (paramInt2 - paramInt3 + j - 1) * paramInt9 + paramInt8, 1, paramArrayOfDouble4, 1 - 1 + (j - 1) * paramInt11 + paramInt10, 1);
                    j += 1;
                }
                blas.dtrmm("Right", "Upper", "No transpose", "Unit", paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, paramInt2 - paramInt3 + 1 - 1 + (1 - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                    blas.dgemm("No transpose", "No transpose", paramInt1, paramInt3, paramInt2 - paramInt3, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9, paramArrayOfDouble1, paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                }
                blas.dtrmm("Right", "Lower", paramString2, "Non-unit", paramInt1, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                    blas.dgemm("No transpose", "Transpose", paramInt1, paramInt2 - paramInt3, paramInt3, -1.0D, paramArrayOfDouble4, paramInt10, paramInt11, paramArrayOfDouble1, paramInt4, paramInt5, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9);
                }
                blas.dtrmm("Right", "Upper", "Transpose", "Unit", paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, paramInt2 - paramInt3 + 1 - 1 + (1 - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                j = 1;
                k = paramInt3 - 1 + 1;
                for (;;)
                {
                    i = 1;
                    for (m = paramInt1 - 1 + 1; m > 0; m--)
                    {
                        paramArrayOfDouble3[(i - 1 + (paramInt2 - paramInt3 + j - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                        i += 1;
                    }
                    j += 1;
                    k--;
                    if (k <= 0) {
                        break;
                    }
                }
            }
        }
        else if (Lsame.lsame(paramString4, "R")) {
            if (Lsame.lsame(paramString3, "F"))
            {
                if (Lsame.lsame(paramString1, "L"))
                {
                    j = 1;
                    for (k = paramInt3 - 1 + 1; k > 0; k--)
                    {
                        Dcopy.dcopy(paramInt2, paramArrayOfDouble3, j - 1 + (1 - 1) * paramInt9 + paramInt8, paramInt9, paramArrayOfDouble4, 1 - 1 + (j - 1) * paramInt11 + paramInt10, 1);
                        j += 1;
                    }
                    blas.dtrmm("Right", "Upper", "Transpose", "Unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                        blas.dgemm("Transpose", "Transpose", paramInt2, paramInt3, paramInt1 - paramInt3, 1.0D, paramArrayOfDouble3, paramInt3 + 1 - 1 + (1 - 1) * paramInt9 + paramInt8, paramInt9, paramArrayOfDouble1, 1 - 1 + (paramInt3 + 1 - 1) * paramInt5 + paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                    }
                    blas.dtrmm("Right", "Upper", str, "Non-unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                        blas.dgemm("Transpose", "Transpose", paramInt1 - paramInt3, paramInt2, paramInt3, -1.0D, paramArrayOfDouble1, 1 - 1 + (paramInt3 + 1 - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11, 1.0D, paramArrayOfDouble3, paramInt3 + 1 - 1 + (1 - 1) * paramInt9 + paramInt8, paramInt9);
                    }
                    blas.dtrmm("Right", "Upper", "No transpose", "Unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    j = 1;
                    for (k = paramInt3 - 1 + 1; k > 0; k--)
                    {
                        i = 1;
                        for (m = paramInt2 - 1 + 1; m > 0; m--)
                        {
                            paramArrayOfDouble3[(j - 1 + (i - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                            i += 1;
                        }
                        j += 1;
                    }
                }
                else if (Lsame.lsame(paramString1, "R"))
                {
                    j = 1;
                    for (k = paramInt3 - 1 + 1; k > 0; k--)
                    {
                        Dcopy.dcopy(paramInt1, paramArrayOfDouble3, 1 - 1 + (j - 1) * paramInt9 + paramInt8, 1, paramArrayOfDouble4, 1 - 1 + (j - 1) * paramInt11 + paramInt10, 1);
                        j += 1;
                    }
                    blas.dtrmm("Right", "Upper", "Transpose", "Unit", paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                        blas.dgemm("No transpose", "Transpose", paramInt1, paramInt3, paramInt2 - paramInt3, 1.0D, paramArrayOfDouble3, 1 - 1 + (paramInt3 + 1 - 1) * paramInt9 + paramInt8, paramInt9, paramArrayOfDouble1, 1 - 1 + (paramInt3 + 1 - 1) * paramInt5 + paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                    }
                    blas.dtrmm("Right", "Upper", paramString2, "Non-unit", paramInt1, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                        blas.dgemm("No transpose", "No transpose", paramInt1, paramInt2 - paramInt3, paramInt3, -1.0D, paramArrayOfDouble4, paramInt10, paramInt11, paramArrayOfDouble1, 1 - 1 + (paramInt3 + 1 - 1) * paramInt5 + paramInt4, paramInt5, 1.0D, paramArrayOfDouble3, 1 - 1 + (paramInt3 + 1 - 1) * paramInt9 + paramInt8, paramInt9);
                    }
                    blas.dtrmm("Right", "Upper", "No transpose", "Unit", paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    j = 1;
                    k = paramInt3 - 1 + 1;
                    for (;;)
                    {
                        i = 1;
                        for (m = paramInt1 - 1 + 1; m > 0; m--)
                        {
                            paramArrayOfDouble3[(i - 1 + (j - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                            i += 1;
                        }
                        j += 1;
                        k--;
                        if (k <= 0) {
                            break;
                        }
                    }
                }
            }
            else if (Lsame.lsame(paramString1, "L"))
            {
                j = 1;
                for (k = paramInt3 - 1 + 1; k > 0; k--)
                {
                    Dcopy.dcopy(paramInt2, paramArrayOfDouble3, paramInt1 - paramInt3 + j - 1 + (1 - 1) * paramInt9 + paramInt8, paramInt9, paramArrayOfDouble4, 1 - 1 + (j - 1) * paramInt11 + paramInt10, 1);
                    j += 1;
                }
                blas.dtrmm("Right", "Lower", "Transpose", "Unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, 1 - 1 + (paramInt1 - paramInt3 + 1 - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                    blas.dgemm("Transpose", "Transpose", paramInt2, paramInt3, paramInt1 - paramInt3, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9, paramArrayOfDouble1, paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                }
                blas.dtrmm("Right", "Lower", str, "Non-unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                    blas.dgemm("Transpose", "Transpose", paramInt1 - paramInt3, paramInt2, paramInt3, -1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9);
                }
                blas.dtrmm("Right", "Lower", "No transpose", "Unit", paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, 1 - 1 + (paramInt1 - paramInt3 + 1 - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                j = 1;
                for (k = paramInt3 - 1 + 1; k > 0; k--)
                {
                    i = 1;
                    for (m = paramInt2 - 1 + 1; m > 0; m--)
                    {
                        paramArrayOfDouble3[(paramInt1 - paramInt3 + j - 1 + (i - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                        i += 1;
                    }
                    j += 1;
                }
            }
            else if (Lsame.lsame(paramString1, "R"))
            {
                j = 1;
                for (k = paramInt3 - 1 + 1; k > 0; k--)
                {
                    Dcopy.dcopy(paramInt1, paramArrayOfDouble3, 1 - 1 + (paramInt2 - paramInt3 + j - 1) * paramInt9 + paramInt8, 1, paramArrayOfDouble4, 1 - 1 + (j - 1) * paramInt11 + paramInt10, 1);
                    j += 1;
                }
                blas.dtrmm("Right", "Lower", "Transpose", "Unit", paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, 1 - 1 + (paramInt2 - paramInt3 + 1 - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                    blas.dgemm("No transpose", "Transpose", paramInt1, paramInt3, paramInt2 - paramInt3, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9, paramArrayOfDouble1, paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                }
                blas.dtrmm("Right", "Lower", paramString2, "Non-unit", paramInt1, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                    blas.dgemm("No transpose", "No transpose", paramInt1, paramInt2 - paramInt3, paramInt3, -1.0D, paramArrayOfDouble4, paramInt10, paramInt11, paramArrayOfDouble1, paramInt4, paramInt5, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9);
                }
                blas.dtrmm("Right", "Lower", "No transpose", "Unit", paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, 1 - 1 + (paramInt2 - paramInt3 + 1 - 1) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                j = 1;
                k = paramInt3 - 1 + 1;
                for (;;)
                {
                    i = 1;
                    for (m = paramInt1 - 1 + 1; m > 0; m--)
                    {
                        paramArrayOfDouble3[(i - 1 + (paramInt2 - paramInt3 + j - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                        i += 1;
                    }
                    j += 1;
                    k--;
                    if (k <= 0) {
                        break;
                    }
                }
            }
        }
    }

    public  void jdlarfb(KBlasSideType paramString1, KBlasTransposeType paramString2, KBlasDirectionType paramString3, KBlasMajorType paramString4, int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble1, int paramInt4, int paramInt5, double[] paramArrayOfDouble2, int paramInt6, int paramInt7, double[] paramArrayOfDouble3, int paramInt8, int paramInt9, double[] paramArrayOfDouble4, int paramInt10, int paramInt11)
    {
        KBlasTransposeType str;
        int i = 0;
        int j = 0;
        if (((paramInt2 > 0 ? 0 : 1) == 0 ? 0 : 1) != 0) {
            return;
        }
        if (paramString2.equals(KBlasTransposeType.NOTRANSPOSE)) {
            str = KBlasTransposeType.TRANSPOSE;
        } else {
            str = KBlasTransposeType.NOTRANSPOSE;
        }
        int k;
        int m;
        if (paramString4.equals(KBlasMajorType.COLUMNWISE))
        {
            if (paramString3.equals(KBlasDirectionType.FORWARD))
            {
                if (paramString1.equals(KBlasSideType.LEFT))
                {
                    j = 1;
                    for (k = paramInt3; k > 0; k--)
                    {
                        jdcopy(paramInt2, paramArrayOfDouble3, j - 1 + paramInt8, paramInt9, paramArrayOfDouble4, (j - 1) * paramInt11 + paramInt10, 1);
                        j += 1;
                    }

                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.UNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                        dgemm(KBlasTransposeType.TRANSPOSE, KBlasTransposeType.NOTRANSPOSE, paramInt2, paramInt3, paramInt1 - paramInt3, 1.0D, paramArrayOfDouble3, paramInt3+ paramInt8, paramInt9, paramArrayOfDouble1, paramInt3 + paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                    }
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, str, KBlasUnitType.NONUNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                        dgemm(KBlasTransposeType.NOTRANSPOSE, KBlasTransposeType.TRANSPOSE, paramInt1 - paramInt3, paramInt2, paramInt3, -1.0D, paramArrayOfDouble1, paramInt3+ paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11, 1.0D, paramArrayOfDouble3, paramInt3+ (1 - 1) * paramInt9 + paramInt8, paramInt9);
                    }
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, KBlasTransposeType.TRANSPOSE, KBlasUnitType.UNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    j = 1;
                    for (k = paramInt3; k > 0; k--)
                    {
                        i = 1;
                        for (m = paramInt2; m > 0; m--)
                        {
                            paramArrayOfDouble3[(j - 1 + (i - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                            i += 1;
                        }
                        j += 1;
                    }
                }
                else if (paramString1.equals(KBlasSideType.RIGHT))
                {
                    j = 1;
                    for (k = paramInt3; k > 0; k--)
                    {
                        jdcopy(paramInt1, paramArrayOfDouble3, (j - 1) * paramInt9 + paramInt8, 1, paramArrayOfDouble4, (j - 1) * paramInt11 + paramInt10, 1);
                        j += 1;
                    }
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.UNIT, paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                        dgemm(KBlasTransposeType.NOTRANSPOSE, KBlasTransposeType.NOTRANSPOSE, paramInt1, paramInt3, paramInt2 - paramInt3, 1.0D, paramArrayOfDouble3, (paramInt3 ) * paramInt9 + paramInt8, paramInt9, paramArrayOfDouble1, paramInt3 + paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                    }
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, paramString2, KBlasUnitType.NONUNIT, paramInt1, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                        dgemm(KBlasTransposeType.NOTRANSPOSE, KBlasTransposeType.TRANSPOSE, paramInt1, paramInt2 - paramInt3, paramInt3, -1.0D, paramArrayOfDouble4, paramInt10, paramInt11, paramArrayOfDouble1, paramInt3 + paramInt4, paramInt5, 1.0D, paramArrayOfDouble3,  (paramInt3 ) * paramInt9 + paramInt8, paramInt9);
                    }
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, KBlasTransposeType.TRANSPOSE, KBlasUnitType.UNIT, paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    j = 1;
                    k = paramInt3;
                    for (;;)
                    {
                        i = 1;
                        for (m = paramInt1; m > 0; m--) {
                            paramArrayOfDouble3[(i - 1 + (j - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                            i += 1;
                        }
                        j += 1;
                        k--;
                        if (k <= 0) {
                            break;
                        }
                    }
                }
            }
            else if (paramString1.equals(KBlasSideType.LEFT))
            {
                j = 1;
                for (k = paramInt3; k > 0; k--)
                {
                    jdcopy(paramInt2, paramArrayOfDouble3, paramInt1 - paramInt3 + j - 1 + paramInt8, paramInt9, paramArrayOfDouble4, (j - 1) * paramInt11 + paramInt10, 1);
                    j += 1;
                }
                jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.UNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt1 - paramInt3 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                    dgemm(KBlasTransposeType.TRANSPOSE, KBlasTransposeType.NOTRANSPOSE, paramInt2, paramInt3, paramInt1 - paramInt3, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9, paramArrayOfDouble1, paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                }
                jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, str, KBlasUnitType.NONUNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                    dgemm(KBlasTransposeType.NOTRANSPOSE, KBlasTransposeType.TRANSPOSE, paramInt1 - paramInt3, paramInt2, paramInt3, -1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9);
                }
                jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, KBlasTransposeType.TRANSPOSE, KBlasUnitType.UNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt1 - paramInt3 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                j = 1;
                for (k = paramInt3; k > 0; k--)
                {
                    i = 1;
                    for (m = paramInt2; m > 0; m--)
                    {
                        paramArrayOfDouble3[(paramInt1 - paramInt3 + j - 1 + (i - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                        i += 1;
                    }
                    j += 1;
                }
            }
            else if (paramString1.equals(KBlasSideType.RIGHT))
            {
                j = 1;
                for (k = paramInt3; k > 0; k--)
                {
                    jdcopy(paramInt1, paramArrayOfDouble3, (paramInt2 - paramInt3 + j - 1) * paramInt9 + paramInt8, 1, paramArrayOfDouble4, (j - 1) * paramInt11 + paramInt10, 1);
                    j += 1;
                }
                jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.UNIT, paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, paramInt2 - paramInt3 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                    dgemm(KBlasTransposeType.NOTRANSPOSE, KBlasTransposeType.NOTRANSPOSE, paramInt1, paramInt3, paramInt2 - paramInt3, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9, paramArrayOfDouble1, paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                }
                jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, paramString2, KBlasUnitType.NONUNIT, paramInt1, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                    dgemm(KBlasTransposeType.NOTRANSPOSE, KBlasTransposeType.TRANSPOSE, paramInt1, paramInt2 - paramInt3, paramInt3, -1.0D, paramArrayOfDouble4, paramInt10, paramInt11, paramArrayOfDouble1, paramInt4, paramInt5, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9);
                }
                jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, KBlasTransposeType.TRANSPOSE, KBlasUnitType.UNIT, paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, paramInt2 - paramInt3 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                j = 1;
                k = paramInt3;
                for (;;)
                {
                    i = 1;
                    for (m = paramInt1; m > 0; m--)
                    {
                        paramArrayOfDouble3[(i - 1 + (paramInt2 - paramInt3 + j - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                        i += 1;
                    }
                    j += 1;
                    k--;
                    if (k <= 0) {
                        break;
                    }
                }
            }
        }
        else if (paramString4.equals(KBlasMajorType.ROWWISE)) {
            if (paramString3.equals(KBlasDirectionType.FORWARD))
            {
                if (paramString1.equals(KBlasSideType.LEFT))
                {
                    j = 1;
                    for (k = paramInt3; k > 0; k--)
                    {
                        jdcopy(paramInt2, paramArrayOfDouble3, j - 1 + paramInt8, paramInt9, paramArrayOfDouble4, (j - 1) * paramInt11 + paramInt10, 1);
                        j += 1;
                    }
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, KBlasTransposeType.TRANSPOSE, KBlasUnitType.UNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                        dgemm(KBlasTransposeType.TRANSPOSE, KBlasTransposeType.TRANSPOSE, paramInt2, paramInt3, paramInt1 - paramInt3, 1.0D, paramArrayOfDouble3, paramInt3 + paramInt8, paramInt9, paramArrayOfDouble1, (paramInt3) * paramInt5 + paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                    }
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, str, KBlasUnitType.NONUNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                        dgemm(KBlasTransposeType.TRANSPOSE, KBlasTransposeType.TRANSPOSE, paramInt1 - paramInt3, paramInt2, paramInt3, -1.0D, paramArrayOfDouble1, (paramInt3) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11, 1.0D, paramArrayOfDouble3, paramInt3+ (1 - 1) * paramInt9 + paramInt8, paramInt9);
                    }
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.UNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    j = 1;
                    for (k = paramInt3; k > 0; k--)
                    {
                        i = 1;
                        for (m = paramInt2; m > 0; m--)
                        {
                            paramArrayOfDouble3[(j - 1 + (i - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                            i += 1;
                        }
                        j += 1;
                    }
                }
                else if (paramString1.equals(KBlasSideType.RIGHT))
                {
                    j = 1;
                    for (k = paramInt3; k > 0; k--)
                    {
                        jdcopy(paramInt1, paramArrayOfDouble3,  (j - 1) * paramInt9 + paramInt8, 1, paramArrayOfDouble4,  (j - 1) * paramInt11 + paramInt10, 1);
                        j += 1;
                    }
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, KBlasTransposeType.TRANSPOSE, KBlasUnitType.UNIT, paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                        dgemm(KBlasTransposeType.NOTRANSPOSE, KBlasTransposeType.TRANSPOSE, paramInt1, paramInt3, paramInt2 - paramInt3, 1.0D, paramArrayOfDouble3,  (paramInt3 ) * paramInt9 + paramInt8, paramInt9, paramArrayOfDouble1,  (paramInt3 ) * paramInt5 + paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                    }
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, paramString2, KBlasUnitType.NONUNIT, paramInt1, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                    if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                        dgemm(KBlasTransposeType.NOTRANSPOSE, KBlasTransposeType.NOTRANSPOSE, paramInt1, paramInt2 - paramInt3, paramInt3, -1.0D, paramArrayOfDouble4, paramInt10, paramInt11, paramArrayOfDouble1, (paramInt3) * paramInt5 + paramInt4, paramInt5, 1.0D, paramArrayOfDouble3,(paramInt3 ) * paramInt9 + paramInt8, paramInt9);
                    }
                    jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.UNIT, paramInt1, paramInt3, 1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                    j = 1;
                    k = paramInt3;
                    for (;;)
                    {
                        i = 1;
                        for (m = paramInt1; m > 0; m--)
                        {
                            paramArrayOfDouble3[(i - 1 + (j - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                            i += 1;
                        }
                        j += 1;
                        k--;
                        if (k <= 0) {
                            break;
                        }
                    }
                }
            }
            else if (paramString1.equals(KBlasSideType.LEFT))
            {
                j = 1;
                for (k = paramInt3; k > 0; k--)
                {
                    jdcopy(paramInt2, paramArrayOfDouble3, paramInt1 - paramInt3 + j - 1  + paramInt8, paramInt9, paramArrayOfDouble4,(j - 1) * paramInt11 + paramInt10, 1);
                    j += 1;
                }
                jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, KBlasTransposeType.TRANSPOSE, KBlasUnitType.UNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble1,(paramInt1 - paramInt3 ) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                    dgemm(KBlasTransposeType.TRANSPOSE, KBlasTransposeType.TRANSPOSE, paramInt2, paramInt3, paramInt1 - paramInt3, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9, paramArrayOfDouble1, paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                }
                jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, str, KBlasUnitType.NONUNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt1 <= paramInt3 ? 0 : 1) != 0) {
                    dgemm(KBlasTransposeType.TRANSPOSE, KBlasTransposeType.TRANSPOSE, paramInt1 - paramInt3, paramInt2, paramInt3, -1.0D, paramArrayOfDouble1, paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9);
                }
                jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.UNIT, paramInt2, paramInt3, 1.0D, paramArrayOfDouble1,(paramInt1 - paramInt3 ) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                j = 1;
                for (k = paramInt3; k > 0; k--)
                {
                    i = 1;
                    for (m = paramInt2; m > 0; m--)
                    {
                        paramArrayOfDouble3[(paramInt1 - paramInt3 + j - 1 + (i - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                        i += 1;
                    }
                    j += 1;
                }
            }
            else if (paramString1.equals(KBlasSideType.RIGHT))
            {
                j = 1;
                for (k = paramInt3; k > 0; k--)
                {
                    jdcopy(paramInt1, paramArrayOfDouble3,(paramInt2 - paramInt3 + j - 1) * paramInt9 + paramInt8, 1, paramArrayOfDouble4,(j - 1) * paramInt11 + paramInt10, 1);
                    j += 1;
                }
                jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, KBlasTransposeType.TRANSPOSE, KBlasUnitType.UNIT, paramInt1, paramInt3, 1.0D, paramArrayOfDouble1,(paramInt2 - paramInt3 ) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                    dgemm(KBlasTransposeType.NOTRANSPOSE, KBlasTransposeType.TRANSPOSE, paramInt1, paramInt3, paramInt2 - paramInt3, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9, paramArrayOfDouble1, paramInt4, paramInt5, 1.0D, paramArrayOfDouble4, paramInt10, paramInt11);
                }
                jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, paramString2, KBlasUnitType.NONUNIT, paramInt1, paramInt3, 1.0D, paramArrayOfDouble2, paramInt6, paramInt7, paramArrayOfDouble4, paramInt10, paramInt11);
                if ((paramInt2 <= paramInt3 ? 0 : 1) != 0) {
                    dgemm(KBlasTransposeType.NOTRANSPOSE, KBlasTransposeType.NOTRANSPOSE, paramInt1, paramInt2 - paramInt3, paramInt3, -1.0D, paramArrayOfDouble4, paramInt10, paramInt11, paramArrayOfDouble1, paramInt4, paramInt5, 1.0D, paramArrayOfDouble3, paramInt8, paramInt9);
                }
                jdtrmm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.UNIT, paramInt1, paramInt3, 1.0D, paramArrayOfDouble1,(paramInt2 - paramInt3 ) * paramInt5 + paramInt4, paramInt5, paramArrayOfDouble4, paramInt10, paramInt11);
                j = 1;
                k = paramInt3;
                for (;;)
                {
                    i = 1;
                    for (m = paramInt1; m > 0; m--)
                    {
                        paramArrayOfDouble3[(i - 1 + (paramInt2 - paramInt3 + j - 1) * paramInt9 + paramInt8)] -= paramArrayOfDouble4[(i - 1 + (j - 1) * paramInt11 + paramInt10)];
                        i += 1;
                    }
                    j += 1;
                    k--;
                    if (k <= 0) {
                        break;
                    }
                }
            }
        }
    }

    public void jdcopy(int n, double[] x, int offsetx, int incx, double[] y, int offsety, int incy)
    {
        if(n<=0){
            return;
        }
        int indexX=offsetx;
        int indexY=offsety;
        for(int i=0;i<n;i++){
            y[indexY]=x[indexX];
            indexY+=incy;
            indexX+=incx;
        }
    }

    public  void jdlarft(KBlasDirectionType paramString1, KBlasMajorType paramString2, int paramInt1, int paramInt2, double[] paramArrayOfDouble1, int paramInt3, int paramInt4, double[] paramArrayOfDouble2, int paramInt5, double[] paramArrayOfDouble3, int paramInt6, int paramInt7)
    {
        int i = 0;
        int j = 0;
        double d = 0.0D;
        if ((paramInt1 != 0 ? 0 : 1) != 0) {
            return;
        }
        int k;
        int m;
        if (paramString1.equals(KBlasDirectionType.FORWARD))
        {
            i = 1;
            for (k = paramInt2; k > 0; k--)
            {
                if ((paramArrayOfDouble2[(i - 1 + paramInt5)] != 0.0D ? 0 : 1) != 0)
                {
                    j = 1;
                    for (m = i; m > 0; m--)
                    {
                        paramArrayOfDouble3[(j - 1 + (i - 1) * paramInt7 + paramInt6)] = 0.0D;
                        j += 1;
                    }
                }
                else
                {
                    d = paramArrayOfDouble1[(i - 1 + (i - 1) * paramInt4 + paramInt3)];
                    paramArrayOfDouble1[(i - 1 + (i - 1) * paramInt4 + paramInt3)] = 1.0D;
                    if (paramString2.equals(KBlasMajorType.COLUMNWISE)) {
                        jdgemv(KBlasTransposeType.TRANSPOSE, paramInt1 - i + 1, i - 1, -paramArrayOfDouble2[(i - 1 + paramInt5)], paramArrayOfDouble1, i - 1  + paramInt3, paramInt4, paramArrayOfDouble1, i - 1 + (i - 1) * paramInt4 + paramInt3, 1, 0.0D, paramArrayOfDouble3,(i - 1) * paramInt7 + paramInt6, 1);
                    } else {
                        jdgemv(KBlasTransposeType.NOTRANSPOSE, i - 1, paramInt1 - i + 1, -paramArrayOfDouble2[(i - 1 + paramInt5)], paramArrayOfDouble1,(i - 1) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble1, i - 1 + (i - 1) * paramInt4 + paramInt3, paramInt4, 0.0D, paramArrayOfDouble3, (i - 1) * paramInt7 + paramInt6, 1);
                    }
                    paramArrayOfDouble1[(i - 1 + (i - 1) * paramInt4 + paramInt3)] = d;
                    jdtrmv(KBlasOrientationType.UPPER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.NONUNIT, i - 1, paramArrayOfDouble3, paramInt6, paramInt7, paramArrayOfDouble3, (i - 1) * paramInt7 + paramInt6, 1);
                    paramArrayOfDouble3[(i - 1 + (i - 1) * paramInt7 + paramInt6)] = paramArrayOfDouble2[(i - 1 + paramInt5)];
                }
                i += 1;
            }
        }
        else
        {
            i = paramInt2;
            for (k = (1 - paramInt2 + -1) / -1; k > 0; k--)
            {
                if ((paramArrayOfDouble2[(i - 1 + paramInt5)] != 0.0D ? 0 : 1) != 0)
                {
                    j = i;
                    for (m = paramInt2 - i + 1; m > 0; m--)
                    {
                        paramArrayOfDouble3[(j - 1 + (i - 1) * paramInt7 + paramInt6)] = 0.0D;
                        j += 1;
                    }
                }
                else
                {
                    if ((i >= paramInt2 ? 0 : 1) != 0)
                    {
                        if (paramString2.equals(KBlasMajorType.COLUMNWISE))
                        {
                            d = paramArrayOfDouble1[(paramInt1 - paramInt2 + i - 1 + (i - 1) * paramInt4 + paramInt3)];
                            paramArrayOfDouble1[(paramInt1 - paramInt2 + i - 1 + (i - 1) * paramInt4 + paramInt3)] = 1.0D;
                            jdgemv(KBlasTransposeType.TRANSPOSE, paramInt1 - paramInt2 + i, paramInt2 - i, -paramArrayOfDouble2[(i - 1 + paramInt5)], paramArrayOfDouble1, (i ) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble1, (i - 1) * paramInt4 + paramInt3, 1, 0.0D, paramArrayOfDouble3, i+ (i - 1) * paramInt7 + paramInt6, 1);
                            paramArrayOfDouble1[(paramInt1 - paramInt2 + i - 1 + (i - 1) * paramInt4 + paramInt3)] = d;
                        }
                        else
                        {
                            d = paramArrayOfDouble1[(i - 1 + (paramInt1 - paramInt2 + i - 1) * paramInt4 + paramInt3)];
                            paramArrayOfDouble1[(i - 1 + (paramInt1 - paramInt2 + i - 1) * paramInt4 + paramInt3)] = 1.0D;
                            jdgemv(KBlasTransposeType.NOTRANSPOSE, paramInt2 - i, paramInt1 - paramInt2 + i, -paramArrayOfDouble2[(i - 1 + paramInt5)], paramArrayOfDouble1, i + paramInt3, paramInt4, paramArrayOfDouble1, i - 1  + paramInt3, paramInt4, 0.0D, paramArrayOfDouble3, i+ (i - 1) * paramInt7 + paramInt6, 1);
                            paramArrayOfDouble1[(i - 1 + (paramInt1 - paramInt2 + i - 1) * paramInt4 + paramInt3)] = d;
                        }
                        jdtrmv(KBlasOrientationType.LOWER, KBlasTransposeType.NOTRANSPOSE, KBlasUnitType.NONUNIT, paramInt2 - i, paramArrayOfDouble3, i+ (i ) * paramInt7 + paramInt6, paramInt7, paramArrayOfDouble3, i+ (i - 1) * paramInt7 + paramInt6, 1);
                    }
                    paramArrayOfDouble3[(i - 1 + (i - 1) * paramInt7 + paramInt6)] = paramArrayOfDouble2[(i - 1 + paramInt5)];
                }
                i += -1;
            }
        }
    }

    public  void jdgeqr2(int paramInt1, int paramInt2, double[] paramArrayOfDouble1, int paramInt3, int paramInt4, double[] paramArrayOfDouble2, int paramInt5, double[] paramArrayOfDouble3, int paramInt6, int[] paramintW) {
        int i = 0;
        int j = 0;
        double d = 0.0D;
        paramintW[0] = 0;
        if ((paramInt1 >= 0 ? 0 : 1) != 0) {
            paramintW[0] = -1;
        } else if ((paramInt2 >= 0 ? 0 : 1) != 0) {
            paramintW[0] = -2;
        } else if ((paramInt4 >= Math.max(1, paramInt1) ? 0 : 1) != 0) {
            paramintW[0] = -4;
        }
        if ((paramintW[0] == 0 ? 0 : 1) != 0) {
            //Xerbla.xerbla("DGEQR2", -paramintW[0]);
            return;
        }
        j = Math.min(paramInt1, paramInt2);
        i = 1;
        for (int k = j; k > 0; k--) {
            jdlarfg_adapter(paramInt1 - i + 1, paramArrayOfDouble1, i - 1 + (i - 1) * paramInt4 + paramInt3, paramArrayOfDouble1, Math.min(i + 1, paramInt1) - 1 + (i - 1) * paramInt4 + paramInt3, 1, paramArrayOfDouble2, i - 1 + paramInt5);
            if ((i >= paramInt2 ? 0 : 1) != 0) {
                d = paramArrayOfDouble1[(i - 1 + (i - 1) * paramInt4 + paramInt3)];
                paramArrayOfDouble1[(i - 1 + (i - 1) * paramInt4 + paramInt3)] = 1.0D;
                jdlarf(KBlasSideType.LEFT, paramInt1 - i + 1, paramInt2 - i, paramArrayOfDouble1, i - 1 + (i - 1) * paramInt4 + paramInt3, 1, paramArrayOfDouble2[(i - 1 + paramInt5)], paramArrayOfDouble1, i - 1 + (i ) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble3, paramInt6);
                paramArrayOfDouble1[(i - 1 + (i - 1) * paramInt4 + paramInt3)] = d;
            }
            i += 1;
        }
    }
    private  void jdlarfg_adapter(int paramInt1, double[] paramArrayOfDouble1, int paramInt2, double[] paramArrayOfDouble2, int paramInt3, int paramInt4, double[] paramArrayOfDouble3, int paramInt5)
    {
        double[] localdoubleW1 = new double[1];
        localdoubleW1[0]=(paramArrayOfDouble1[paramInt2]);
        double[] localdoubleW2 = new double[1];
        localdoubleW2[0]=(paramArrayOfDouble3[paramInt5]);
        jdlarfg(paramInt1, localdoubleW1, paramArrayOfDouble2, paramInt3, paramInt4, localdoubleW2);
        paramArrayOfDouble1[paramInt2] = localdoubleW1[0];
        paramArrayOfDouble3[paramInt5] = localdoubleW2[0];
    }

    public  void jdlarf(KBlasSideType paramString, int paramInt1, int paramInt2, double[] paramArrayOfDouble1, int paramInt3, int paramInt4, double paramDouble, double[] paramArrayOfDouble2, int paramInt5, int paramInt6, double[] paramArrayOfDouble3, int paramInt7)
    {
        if (paramString.equals(KBlasSideType.LEFT))
        {
            if ((paramDouble == 0.0D ? 0 : 1) != 0)
            {
                jdgemv(KBlasTransposeType.TRANSPOSE, paramInt1, paramInt2, 1.0D, paramArrayOfDouble2, paramInt5, paramInt6, paramArrayOfDouble1, paramInt3, paramInt4, 0.0D, paramArrayOfDouble3, paramInt7, 1);
                jdger(paramInt1, paramInt2, -paramDouble, paramArrayOfDouble1, paramInt3, paramInt4, paramArrayOfDouble3, paramInt7, 1, paramArrayOfDouble2, paramInt5, paramInt6);
            }
        }
        else if ((paramDouble == 0.0D ? 0 : 1) != 0)
        {
            jdgemv(KBlasTransposeType.NOTRANSPOSE, paramInt1, paramInt2, 1.0D, paramArrayOfDouble2, paramInt5, paramInt6, paramArrayOfDouble1, paramInt3, paramInt4, 0.0D, paramArrayOfDouble3, paramInt7, 1);
            jdger(paramInt1, paramInt2, -paramDouble, paramArrayOfDouble3, paramInt7, 1, paramArrayOfDouble1, paramInt3, paramInt4, paramArrayOfDouble2, paramInt5, paramInt6);
        }
    }

    public double dnrm2(int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3)
    {
        double d1 = 0.0D;
        double d2 = 0.0D;
        double d3 = 0.0D;
        double d4 = 0.0D;
        int i = 0;
        double d5 = 0.0D;
        if (((paramInt3 >= 1 ? 0 : 1) == 0 ? 0 : 1) != 0)
        {
            d2 = 0.0D;
        }
        else if ((paramInt1 != 1 ? 0 : 1) != 0)
        {
            d2 = Math.abs(paramArrayOfDouble[(paramInt2)]);
        }
        else
        {
            d3 = 0.0D;
            d4 = 1.0D;
            i = 1;
            for (int j = (1 + (paramInt1 - 1) * paramInt3 - 1 + paramInt3) / paramInt3; j > 0; j--)
            {
                if ((paramArrayOfDouble[(i - 1 + paramInt2)] == 0.0D ? 0 : 1) != 0)
                {
                    d1 = Math.abs(paramArrayOfDouble[(i - 1 + paramInt2)]);
                    if ((d3 >= d1 ? 0 : 1) != 0)
                    {
                        d4 = 1.0D + d4 * Math.pow(d3 / d1, 2);
                        d3 = d1;
                    }
                    else
                    {
                        d4 += Math.pow(d1 / d3, 2);
                    }
                }
                i += paramInt3;
            }
            d2 = d3 * Math.sqrt(d4);
        }
        d5 = d2;
        return d5;
    }

    public double dlapy2(double paramDouble1, double paramDouble2)
    {
        double d1 = 0.0D;
        double d2 = 0.0D;
        double d3 = 0.0D;
        double d4 = 0.0D;
        double d5 = 0.0D;
        d2 = Math.abs(paramDouble1);
        d3 = Math.abs(paramDouble2);
        d1 = Math.max(d2, d3);
        d4 = Math.min(d2, d3);
        if ((d4 != 0.0D ? 0 : 1) != 0) {
            d5 = d1;
        } else {
            d5 = d1 * Math.sqrt(1.0D + Math.pow(d4 / d1, 2));
        }
        return d5;
    }

    private double dsign(double paramDouble1, double paramDouble2)
    {
        if (paramDouble2 > 0.0D)
            return Math.abs(paramDouble1);
        if (paramDouble2 < 0.0D) {
            return -Math.abs(paramDouble1);
        }
        return 0.0D;
    }

    public  void jdlarfg(int paramInt1, double[] paramdoubleW1, double[] paramArrayOfDouble, int paramInt2, int paramInt3, double[] paramdoubleW2)
    {
        int i = 0;
        int j = 0;
        double d1 = 0.0D;
        double d2 = 0.0D;
        double d3 = 0.0D;
        double d4 = 0.0D;
        if ((paramInt1 > 1 ? 0 : 1) != 0)
        {
            paramdoubleW2[0] = 0.0D;
            return;
        }
        d4 = dnrm2(paramInt1 - 1, paramArrayOfDouble, paramInt2, paramInt3);
        if ((d4 != 0.0D ? 0 : 1) != 0)
        {
            paramdoubleW2[0] = 0.0D;
        }
        else
        {
            d1 = -dsign(dlapy2(paramdoubleW1[0], d4), paramdoubleW1[0]);
            d3 = PrimitiveHelper.DOUBLE_MIN_VALUE();
            if ((Math.abs(d1) >= d3 ? 0 : 1) != 0)
            {
                d2 = 1.0D / d3;
                j = 0;
                do
                {
                    j += 1;
                    jdscal(paramInt1 - 1, d2, paramArrayOfDouble, paramInt2, paramInt3);
                    d1 *= d2;
                    paramdoubleW1[0] *= d2;
                } while ((Math.abs(d1) >= d3 ? 0 : 1) != 0);
                d4 = dnrm2(paramInt1 - 1, paramArrayOfDouble, paramInt2, paramInt3);
                d1 = -dsign(dlapy2(paramdoubleW1[0], d4), paramdoubleW1[0]);
                paramdoubleW2[0] = ((d1 - paramdoubleW1[0]) / d1);
                jdscal(paramInt1 - 1, 1.0D / (paramdoubleW1[0] - d1), paramArrayOfDouble, paramInt2, paramInt3);
                paramdoubleW1[0] = d1;
                i = 1;
                for (int k = j; k > 0; k--)
                {
                    paramdoubleW1[0] *= d3;
                    i += 1;
                }
            }
            else
            {
                paramdoubleW2[0] = ((d1 - paramdoubleW1[0]) / d1);
                jdscal(paramInt1 - 1, 1.0D / (paramdoubleW1[0] - d1), paramArrayOfDouble, paramInt2, paramInt3);
                paramdoubleW1[0] = d1;
            }
        }
    }


    
    public void jshutdown() {

    }



    public  void jdtrti2(KBlasOrientationType paramString1, KBlasUnitType paramString2, int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3, int[] paramintW)
    {
        // print(paramArrayOfDouble,"inside jdtrti2");
        boolean bool1 = false;
        boolean bool2 = false;
        int i = 0;
        double d = 0.0D;
        paramintW[0] = 0;
        bool2 = paramString1.equals(KBlasOrientationType.UPPER);
        bool1 = paramString2.equals(KBlasUnitType.NONUNIT);
        if ((((!bool2)) && ((!paramString1.equals(KBlasOrientationType.LOWER))) ? 1 : 0) != 0) {
            paramintW[0] = -1;
        } else if ((((!bool1)) && ((!paramString2.equals(KBlasUnitType.UNIT))) ? 1 : 0) != 0) {
            paramintW[0] = -2;
        } else if ((paramInt1 <0)) {
            paramintW[0] = -3;
        } else if ((paramInt3 < Math.max(1, paramInt1))) {
            paramintW[0] = -5;
        }
        if ((paramintW[0] !=0))
        {
            //Xerbla.xerbla("DTRTI2", -paramintW[0]);
            return;
        }
        int j;
        if (bool2)
        {
            i = 1;
            for (j = paramInt1; j > 0; j--)
            {
                if (bool1)
                {
                    paramArrayOfDouble[(i - 1 + (i - 1) * paramInt3 + paramInt2)] = (1.0D / paramArrayOfDouble[(i - 1 + (i - 1) * paramInt3 + paramInt2)]);
                    d = -paramArrayOfDouble[(i - 1 + (i - 1) * paramInt3 + paramInt2)];
                }
                else
                {
                    d = -1.0D;
                }
                //print(paramArrayOfDouble,"before jdtrmv");
                jdtrmv(KBlasOrientationType.UPPER, KBlasTransposeType.NOTRANSPOSE, paramString2, i - 1, paramArrayOfDouble, paramInt2, paramInt3, paramArrayOfDouble, (i - 1) * paramInt3 + paramInt2, 1);
                //print(paramArrayOfDouble, "after jdtrmv");
                jdscal(i - 1, d, paramArrayOfDouble, (i - 1) * paramInt3 + paramInt2, 1);
                //print(paramArrayOfDouble, "after jdscal");
                i++;
            }
        }
        else
        {
            i = paramInt1;
            for (j = paramInt1; j > 0; j--)
            {
                if (bool1) {
                    paramArrayOfDouble[(i - 1 + (i - 1) * paramInt3 + paramInt2)] = (1.0D / paramArrayOfDouble[(i - 1 + (i - 1) * paramInt3 + paramInt2)]);
                    d = -paramArrayOfDouble[(i - 1 + (i - 1) * paramInt3 + paramInt2)];
                }
                else
                {
                    d = -1.0D;
                }
                if ((i < paramInt1))
                {
                    jdtrmv(KBlasOrientationType.LOWER, KBlasTransposeType.NOTRANSPOSE, paramString2, paramInt1 - i, paramArrayOfDouble, i + (i ) * paramInt3 + paramInt2, paramInt3, paramArrayOfDouble, i + (i - 1) * paramInt3 + paramInt2, 1);
                    jdscal(paramInt1 - i, d, paramArrayOfDouble, i + (i - 1) * paramInt3 + paramInt2, 1);
                }
                i += -1;
            }
        }
    }

    private  void jprint(double[] paramArrayOfDouble, String s) {
        String t=s+": ";

        for(int i=0;i<paramArrayOfDouble.length;i++){
            t = t+ paramArrayOfDouble[i]+" ";
        }
        System.out.println(t);
    }

    public  void jdtrmv(KBlasOrientationType paramString1, KBlasTransposeType paramString2, KBlasUnitType paramString3, int paramInt1, double[] paramArrayOfDouble1, int paramInt2, int paramInt3, double[] paramArrayOfDouble2, int paramInt4, int paramInt5)
    {
        double d = 0.0D;
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        int n = 0;
        int i1 = 0;
        boolean bool = false;
    /*    j = 0;
        if ((((!paramString1.equals(KBlasOrientationType.UPPER))) && ((!paramString1.equals(KBlasOrientationType.LOWER))) ? 1 : 0) != 0)
        {
            j = 1;
        }
        else
        {
           // if ((((!paramString2.equals(KBlasTransposeType.NOTRANSPOSE))) && ((!paramString2.equals(KBlasTransposeType.TRANSPOSE))) ? 1 : 0) != 0) {}
            if (((!paramString2.equals(KBlasTransposeType.CONJUGATE)) ? 1 : 0) != 0) {
                j = 2;
            } else if ((((!paramString3.equals(KBlasUnitType.UNIT))) && ((!paramString3.equals(KBlasUnitType.NONUNIT))) ? 1 : 0) != 0) {
                j = 3;
            } else if ((paramInt1 <0)) {
                j = 4;
            } else if ((paramInt3 >= Math.max(1, paramInt1) ^ true)) {
                j = 6;
            } else if ((paramInt5 != 0 ^ true)) {
                j = 8;
            }
        }
        if ((j !=0))
        {
            //Xerbla.xerbla("DTRMV ", j);
            return;
        }
        if ((paramInt1 != 0 ^ true)) {
            return;
        }*/
        bool = paramString3.equals(KBlasUnitType.NONUNIT);
        if ((paramInt5 <= 0)) {
            i1 = 1 - (paramInt1 - 1) * paramInt5;
        } else if ((paramInt5 != 1)) {
            i1 = 1;
        }
        int i2;
        int i3;
        if (paramString2.equals(KBlasTransposeType.NOTRANSPOSE))
        {
            if (paramString1.equals(KBlasOrientationType.UPPER))
            {
                if ((paramInt5 ==1))
                {
                    m = 1;
                    for (i2 = paramInt1; i2 > 0; i2--)
                    {
                        if ((paramArrayOfDouble2[(m - 1 + paramInt4)] !=0.0D))
                        {
                            d = paramArrayOfDouble2[(m - 1 + paramInt4)];
                            i = 1;
                            for (i3 = m - 1; i3 > 0; i3--)
                            {
                                paramArrayOfDouble2[(i - 1 + paramInt4)] += d * paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt3 + paramInt2)];
                                i++;
                            }
                            if (bool) {
                                paramArrayOfDouble2[(m - 1 + paramInt4)] *= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt3 + paramInt2)];
                            }
                        }
                        m++;
                    }
                }
                else
                {
                    n = i1;
                    m = 1;
                    for (i2 = paramInt1; i2 > 0; i2--)
                    {
                        if ((paramArrayOfDouble2[(n - 1 + paramInt4)] !=0.0D))
                        {
                            d = paramArrayOfDouble2[(n - 1 + paramInt4)];
                            k = i1;
                            i = 1;
                            for (i3 = m - 1; i3 > 0; i3--)
                            {
                                paramArrayOfDouble2[(k - 1 + paramInt4)] += d * paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt3 + paramInt2)];
                                k += paramInt5;
                                i++;
                            }
                            if (bool) {
                                paramArrayOfDouble2[(n - 1 + paramInt4)] *= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt3 + paramInt2)];
                            }
                        }
                        n += paramInt5;
                        m++;
                    }
                }
            }
            else if ((paramInt5 ==1))
            {
                m = paramInt1;
                for (i2 = paramInt1; i2 > 0; i2--)
                {
                    if ((paramArrayOfDouble2[(m - 1 + paramInt4)] !=0.0D))
                    {
                        d = paramArrayOfDouble2[(m - 1 + paramInt4)];
                        i = paramInt1;
                        for (i3 = -(m - paramInt1 ); i3 > 0; i3--)
                        {
                            paramArrayOfDouble2[(i - 1 + paramInt4)] += d * paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt3 + paramInt2)];
                            i += -1;
                        }
                        if (bool) {
                            paramArrayOfDouble2[(m - 1 + paramInt4)] *= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt3 + paramInt2)];
                        }
                    }
                    m += -1;
                }
            }
            else
            {
                i1 += (paramInt1 - 1) * paramInt5;
                n = i1;
                m = paramInt1;
                for (i2 = paramInt1; i2 > 0; i2--)
                {
                    if ((paramArrayOfDouble2[(n - 1 + paramInt4)] !=0.0D))
                    {
                        d = paramArrayOfDouble2[(n - 1 + paramInt4)];
                        k = i1;
                        i = paramInt1;
                        for (i3 = -(m  - paramInt1 ); i3 > 0; i3--)
                        {
                            paramArrayOfDouble2[(k - 1 + paramInt4)] += d * paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt3 + paramInt2)];
                            k -= paramInt5;
                            i += -1;
                        }
                        if (bool) {
                            paramArrayOfDouble2[(n - 1 + paramInt4)] *= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt3 + paramInt2)];
                        }
                    }
                    n -= paramInt5;
                    m += -1;
                }
            }
        }
        else if (paramString1.equals(KBlasOrientationType.UPPER))
        {
            if ((paramInt5 ==1))
            {
                m = paramInt1;
                for (i2 =  paramInt1; i2 > 0; i2--)
                {
                    d = paramArrayOfDouble2[(m - 1 + paramInt4)];
                    if (bool) {
                        d *= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt3 + paramInt2)];
                    }
                    i = m - 1;
                    for (i3 = (m - 1) ; i3 > 0; i3--)
                    {
                        d += paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt3 + paramInt2)] * paramArrayOfDouble2[(i - 1 + paramInt4)];
                        i += -1;
                    }
                    paramArrayOfDouble2[(m - 1 + paramInt4)] = d;
                    m += -1;
                }
            }
            else
            {
                n = i1 + (paramInt1 - 1) * paramInt5;
                m = paramInt1;
                for (i2 = -paramInt1 ; i2 > 0; i2--)
                {
                    d = paramArrayOfDouble2[(n - 1 + paramInt4)];
                    k = n;
                    if (bool) {
                        d *= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt3 + paramInt2)];
                    }
                    i = m - 1;
                    for (i3 =  (m - 1); i3 > 0; i3--)
                    {
                        k -= paramInt5;
                        d += paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt3 + paramInt2)] * paramArrayOfDouble2[(k - 1 + paramInt4)];
                        i += -1;
                    }
                    paramArrayOfDouble2[(n - 1 + paramInt4)] = d;
                    n -= paramInt5;
                    m += -1;
                }
            }
        }
        else if ((paramInt5 ==1))
        {
            m = 1;
            for (i2 = paramInt1; i2 > 0; i2--)
            {
                d = paramArrayOfDouble2[(m - 1 + paramInt4)];
                if (bool) {
                    d *= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt3 + paramInt2)];
                }
                i = m + 1;
                for (i3 = paramInt1 - (m + 1) + 1; i3 > 0; i3--)
                {
                    d += paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt3 + paramInt2)] * paramArrayOfDouble2[(i - 1 + paramInt4)];
                    i++;
                }
                paramArrayOfDouble2[(m - 1 + paramInt4)] = d;
                m++;
            }
        }
        else
        {
            n = i1;
            m = 1;
            for (i2 = paramInt1; i2 > 0; i2--)
            {
                d = paramArrayOfDouble2[(n - 1 + paramInt4)];
                k = n;
                if (bool) {
                    d *= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt3 + paramInt2)];
                }
                i = m + 1;
                for (i3 = paramInt1 - (m + 1) + 1; i3 > 0; i3--)
                {
                    k += paramInt5;
                    d += paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt3 + paramInt2)] * paramArrayOfDouble2[(k - 1 + paramInt4)];
                    i++;
                }
                paramArrayOfDouble2[(n - 1 + paramInt4)] = d;
                n += paramInt5;
                m++;
            }
        }
    }

    public  void jdtrmm(KBlasSideType paramString1, KBlasOrientationType paramString2, KBlasTransposeType paramString3, KBlasUnitType paramString4, int paramInt1, int paramInt2, double paramDouble, double[] paramArrayOfDouble1, int paramInt3, int paramInt4, double[] paramArrayOfDouble2, int paramInt5, int paramInt6)
    {
        double d = 0.0D;
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        int n = 0;
        boolean bool1 = false;
        boolean bool2 = false;
        boolean bool3 = false;
        bool1 = paramString1.equals(KBlasSideType.LEFT);
        if (bool1) {
            n = paramInt1;
        } else {
            n = paramInt2;
        }
        bool2 = paramString4.equals(KBlasUnitType.NONUNIT);
        bool3 = paramString2.equals(KBlasOrientationType.UPPER);
       /* j = 0;
        if ((((!bool1)) && ((!paramString1.equals(KBlasSideType.RIGHT))) ? 1 : 0) != 0)
        {
            j = 1;
        }
        else if ((((!bool3)) && ((!paramString2.equals(KBlasOrientationType.LOWER))) ? 1 : 0) != 0)
        {
            j = 2;
        }
        else
        {
           // if ((((!paramString3.equals(KBlasTransposeType.NOTRANSPOSE))) && ((!paramString3.equals(KBlasTransposeType.TRANSPOSE))) ? 1 : 0) != 0) {}
            if (((!paramString3.equals(KBlasTransposeType.CONJUGATE)) ? 1 : 0) != 0) {
                j = 3;
            } else if ((((!paramString4.equals(KBlasUnitType.UNIT))) && ((!paramString4.equals(KBlasUnitType.NONUNIT))) ? 1 : 0) != 0) {
                j = 4;
            } else if ((paramInt1 <0)) {
                j = 5;
            } else if ((paramInt2 <0)) {
                j = 6;
            } else if ((paramInt4 >= Math.max(1, n) ^ true)) {
                j = 9;
            } else if ((paramInt6 >= Math.max(1, paramInt1) ^ true)) {
                j = 11;
            }
        }
        if ((j !=0))
        {
            //Xerbla.xerbla("jdtrmm ", j);
            return;
        }
        if ((paramInt2 != 0 ^ true)) {
            return;
        }*/
        int i1;
        int i2;
        if ((paramDouble ==0))
        {
            k = 1;
            for (i1 = paramInt2; i1 > 0; i1--)
            {
                i = 1;
                for (i2 = paramInt1; i2 > 0; i2--)
                {
                    paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = 0.0D;
                    i++;
                }
                k++;
            }
            return;
        }
        int i3;
        if (bool1)
        {
            if (paramString3.equals(KBlasTransposeType.NOTRANSPOSE))
            {
                if (bool3)
                {
                    k = 1;
                    for (i1 = paramInt2; i1 > 0; i1--)
                    {
                        m = 1;
                        for (i2 = paramInt1; i2 > 0; i2--)
                        {
                            if ((paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)] !=0.0D))
                            {
                                d = paramDouble * paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)];
                                i = 1;
                                for (i3 = m - 1; i3 > 0; i3--)
                                {
                                    paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] += d * paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt4 + paramInt3)];
                                    i++;
                                }
                                if (bool2) {
                                    d *= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt4 + paramInt3)];
                                }
                                paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)] = d;
                            }
                            m++;
                        }
                        k++;
                    }
                }
                else
                {
                    k = 1;
                    for (i1 = paramInt2; i1 > 0; i1--)
                    {
                        m = paramInt1;
                        for (i2 = paramInt1; i2 > 0; i2--)
                        {
                            if ((paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)] !=0.0D))
                            {
                                d = paramDouble * paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)];
                                paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)] = d;
                                if (bool2) {
                                    paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)] *= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt4 + paramInt3)];
                                }
                                i = m + 1;
                                for (i3 = paramInt1 - (m + 1) + 1; i3 > 0; i3--)
                                {
                                    paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] += d * paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt4 + paramInt3)];
                                    i++;
                                }
                            }
                            m += -1;
                        }
                        k++;
                    }
                }
            }
            else if (bool3)
            {
                k = 1;
                for (i1 = paramInt2; i1 > 0; i1--)
                {
                    i = paramInt1;
                    for (i2 = paramInt1; i2 > 0; i2--)
                    {
                        d = paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)];
                        if (bool2) {
                            d *= paramArrayOfDouble1[(i - 1 + (i - 1) * paramInt4 + paramInt3)];
                        }
                        m = 1;
                        for (i3 = i - 1; i3 > 0; i3--)
                        {
                            d += paramArrayOfDouble1[(m - 1 + (i - 1) * paramInt4 + paramInt3)] * paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)];
                            m++;
                        }
                        paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = (paramDouble * d);
                        i += -1;
                    }
                    k++;
                }
            }
            else
            {
                k = 1;
                for (i1 = paramInt2; i1 > 0; i1--)
                {
                    i = 1;
                    for (i2 = paramInt1; i2 > 0; i2--)
                    {
                        d = paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)];
                        if (bool2) {
                            d *= paramArrayOfDouble1[(i - 1 + (i - 1) * paramInt4 + paramInt3)];
                        }
                        m = i + 1;
                        for (i3 = paramInt1 - (i + 1) + 1; i3 > 0; i3--)
                        {
                            d += paramArrayOfDouble1[(m - 1 + (i - 1) * paramInt4 + paramInt3)] * paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)];
                            m++;
                        }
                        paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = (paramDouble * d);
                        i++;
                    }
                    k++;
                }
            }
        }
        else if (paramString3.equals(KBlasTransposeType.NOTRANSPOSE))
        {
            if (bool3)
            {
                k = paramInt2;
                for (i1 = paramInt2 ; i1 > 0; i1--)
                {
                    d = paramDouble;
                    if (bool2) {
                        d *= paramArrayOfDouble1[(k - 1 + (k - 1) * paramInt4 + paramInt3)];
                    }
                    i = 1;
                    for (i2 = paramInt1; i2 > 0; i2--)
                    {
                        paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = (d * paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)]);
                        i++;
                    }
                    m = 1;
                    for (i2 = k - 1; i2 > 0; i2--)
                    {
                        if ((paramArrayOfDouble1[(m - 1 + (k - 1) * paramInt4 + paramInt3)] !=0.0D))
                        {
                            d = paramDouble * paramArrayOfDouble1[(m - 1 + (k - 1) * paramInt4 + paramInt3)];
                            i = 1;
                            for (i3 = paramInt1; i3 > 0; i3--)
                            {
                                paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] += d * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)];
                                i++;
                            }
                        }
                        m++;
                    }
                    k += -1;
                }
            }
            else
            {
                k = 1;
                for (i1 = paramInt2; i1 > 0; i1--)
                {
                    d = paramDouble;
                    if (bool2) {
                        d *= paramArrayOfDouble1[(k - 1 + (k - 1) * paramInt4 + paramInt3)];
                    }
                    i = 1;
                    for (i2 = paramInt1; i2 > 0; i2--)
                    {
                        paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = (d * paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)]);
                        i++;
                    }
                    m = k + 1;
                    for (i2 = paramInt2 - (k + 1) + 1; i2 > 0; i2--)
                    {
                        if ((paramArrayOfDouble1[(m - 1 + (k - 1) * paramInt4 + paramInt3)] !=0.0D))
                        {
                            d = paramDouble * paramArrayOfDouble1[(m - 1 + (k - 1) * paramInt4 + paramInt3)];
                            i = 1;
                            for (i3 = paramInt1; i3 > 0; i3--)
                            {
                                paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] += d * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)];
                                i++;
                            }
                        }
                        m++;
                    }
                    k++;
                }
            }
        }
        else if (bool3)
        {
            m = 1;
            for (i1 = paramInt2; i1 > 0; i1--)
            {
                k = 1;
                for (i2 = m - 1; i2 > 0; i2--)
                {
                    if ((paramArrayOfDouble1[(k - 1 + (m - 1) * paramInt4 + paramInt3)] !=0.0D))
                    {
                        d = paramDouble * paramArrayOfDouble1[(k - 1 + (m - 1) * paramInt4 + paramInt3)];
                        i = 1;
                        for (i3 = paramInt1; i3 > 0; i3--)
                        {
                            paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] += d * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)];
                            i++;
                        }
                    }
                    k++;
                }
                d = paramDouble;
                if (bool2) {
                    d *= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt4 + paramInt3)];
                }
                if ((d !=1.0D))
                {
                    i = 1;
                    for (i2 = paramInt1; i2 > 0; i2--)
                    {
                        paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)] = (d * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)]);
                        i++;
                    }
                }
                m++;
            }
        }
        else
        {
            m = paramInt2;
            for (i1 = paramInt2; i1 > 0; i1--)
            {
                k = m + 1;
                for (i2 = paramInt2 - (m + 1) + 1; i2 > 0; i2--)
                {
                    if ((paramArrayOfDouble1[(k - 1 + (m - 1) * paramInt4 + paramInt3)] !=0.0D))
                    {
                        d = paramDouble * paramArrayOfDouble1[(k - 1 + (m - 1) * paramInt4 + paramInt3)];
                        i = 1;
                        for (i3 = paramInt1; i3 > 0; i3--)
                        {
                            paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] += d * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)];
                            i++;
                        }
                    }
                    k++;
                }
                d = paramDouble;
                if (bool2) {
                    d *= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt4 + paramInt3)];
                }
                if ((d !=1.0D))
                {
                    i = 1;
                    for (i2 = paramInt1; i2 > 0; i2--)
                    {
                        paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)] = (d * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)]);
                        i++;
                    }
                }
                m += -1;
            }
        }
    }

    public void jdtrtri(KBlasOrientationType paramString1, KBlasUnitType paramString2, int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3, int[] paramintW)
    {
        boolean bool1 = false;
        boolean bool2 = false;
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        paramintW[0] = 0;
        bool2 = paramString1.equals(KBlasOrientationType.UPPER);
        bool1 = paramString2.equals(KBlasUnitType.NONUNIT);
        if ((((!bool2)) && ((!paramString1.equals(KBlasOrientationType.LOWER))) ? 1 : 0) != 0) {
            paramintW[0] = -1;
        } else if ((((!bool1)) && ((!paramString2.equals(KBlasUnitType.UNIT))) ? 1 : 0) != 0) {
            paramintW[0] = -2;
        } else if ((paramInt1 <0)) {
            paramintW[0] = -3;
        } else if ((paramInt3 < Math.max(1, paramInt1))) {
            paramintW[0] = -5;
        }
        if (( paramintW[0] !=0))
        {
            //Xerbla.xerbla("DTRTRI", - paramintW[0]);
            return;
        }
        if ((paramInt1 == 0)) {
            return;
        }
        int n;
        if (bool1)
        {
            paramintW[0] = 1;
            for (n = paramInt1; n > 0; n--)
            {
                if ((paramArrayOfDouble[( paramintW[0] - 1 + ( paramintW[0] - 1) * paramInt3 + paramInt2)] ==0)) {
                    return;
                }
                paramintW[0]++;
            }
            paramintW[0] = 0;
        }
        k = ilaenv(1, "DTRTRI","", paramInt1, -1, -1, -1); //64
        if (((k >= paramInt1)))
        {
            jdtrti2(paramString1, paramString2, paramInt1, paramArrayOfDouble, paramInt2, paramInt3, paramintW);

        }
        else if (bool2)
        {
            i = 1;
            for (n = (int)floorDiv((paramInt1 - 1 + k), k); n > 0; n--)
            {
                j = Math.min(k, paramInt1 - i + 1);
                jdtrmm(KBlasSideType.LEFT, KBlasOrientationType.UPPER, KBlasTransposeType.NOTRANSPOSE, paramString2, i - 1, j, 1.0D, paramArrayOfDouble, paramInt2, paramInt3, paramArrayOfDouble, (i - 1) * paramInt3 + paramInt2, paramInt3);
                jdtrsm(KBlasSideType.RIGHT, KBlasOrientationType.UPPER, KBlasTransposeType.NOTRANSPOSE, paramString2, i - 1, j, -1.0D, paramArrayOfDouble, i - 1 + (i - 1) * paramInt3 + paramInt2, paramInt3, paramArrayOfDouble, (i - 1) * paramInt3 + paramInt2, paramInt3);
                jdtrti2(KBlasOrientationType.UPPER, paramString2, j, paramArrayOfDouble, i - 1 + (i - 1) * paramInt3 + paramInt2, paramInt3, paramintW);
                i += k;
            }
        }
        else
        {

            m = floorDiv((paramInt1 - 1), k) * k + 1;
            i = m;
            for (n = floorDiv((1 - m + -k), -k); n > 0; n--)
            {
                j = Math.min(k, paramInt1 - i + 1);
                if ((i + j <= paramInt1))
                {
                    jdtrmm(KBlasSideType.LEFT, KBlasOrientationType.LOWER, KBlasTransposeType.NOTRANSPOSE, paramString2, paramInt1 - i - j + 1, j, 1.0D, paramArrayOfDouble, i + j - 1 + (i + j - 1) * paramInt3 + paramInt2, paramInt3, paramArrayOfDouble, i + j - 1 + (i - 1) * paramInt3 + paramInt2, paramInt3);
                    jdtrsm(KBlasSideType.RIGHT, KBlasOrientationType.LOWER, KBlasTransposeType.NOTRANSPOSE, paramString2, paramInt1 - i - j + 1, j, -1.0D, paramArrayOfDouble, i - 1 + (i - 1) * paramInt3 + paramInt2, paramInt3, paramArrayOfDouble, i + j - 1 + (i - 1) * paramInt3 + paramInt2, paramInt3);
                }
                jdtrti2(KBlasOrientationType.LOWER, paramString2, j, paramArrayOfDouble, i - 1 + (i - 1) * paramInt3 + paramInt2, paramInt3, paramintW);
                i += -k;
            }
        }
    }





    public  void jdgemv(KBlasTransposeType paramString, int paramInt1, int paramInt2, double paramDouble1, double[] paramArrayOfDouble1, int paramInt3, int paramInt4, double[] paramArrayOfDouble2, int paramInt5, int paramInt6, double paramDouble2, double[] paramArrayOfDouble3, int paramInt7, int paramInt8)
    {
        double d = 0.0D;
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        int n = 0;
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;

        if (paramString.equals(KBlasTransposeType.NOTRANSPOSE))
        {
            i5 = paramInt2;
            i6 = paramInt1;
        }
        else
        {
            i5 = paramInt1;
            i6 = paramInt2;
        }
        if ((paramInt6 >0)) {
            i3 = 1;
        } else {
            i3 = 1 - (i5 - 1) * paramInt6;
        }
        if ((paramInt8 >0)) {
            i4 = 1;
        } else {
            i4 = 1 - (i6 - 1) * paramInt8;
        }
        int i7;
        if ((paramDouble2 !=1.0D)) {
            if ((paramInt8 ==1))
            {
                if ((paramDouble2 ==0))
                {
                    i = 1;
                    for (i7 = i6; i7 > 0; i7--)
                    {
                        paramArrayOfDouble3[(i - 1 + paramInt7)] = 0.0D;
                        i++;
                    }
                }
                else
                {
                    i = 1;
                    for (i7 = i6; i7 > 0; i7--)
                    {
                        paramArrayOfDouble3[(i - 1 + paramInt7)] = (paramDouble2 * paramArrayOfDouble3[(i - 1 + paramInt7)]);
                        i++;
                    }
                }
            }
            else
            {
                m = i4;
                if ((paramDouble2 ==0))
                {
                    i = 1;
                    for (i7 = i6; i7 > 0; i7--)
                    {
                        paramArrayOfDouble3[(m - 1 + paramInt7)] = 0.0D;
                        m += paramInt8;
                        i++;
                    }
                }
                else
                {
                    i = 1;
                    for (i7 = i6; i7 > 0; i7--)
                    {
                        paramArrayOfDouble3[(m - 1 + paramInt7)] = (paramDouble2 * paramArrayOfDouble3[(m - 1 + paramInt7)]);
                        m += paramInt8;
                        i++;
                    }
                }
            }
        }
        if ((paramDouble1 ==0)) {
            return;
        }
        int i8;
        if (paramString.equals(KBlasTransposeType.NOTRANSPOSE))
        {
            i1 = i3;
            if ((paramInt8 ==1))
            {
                n = 1;
                for (i7 = paramInt2; i7 > 0; i7--)
                {
                    if ((paramArrayOfDouble2[(i1 - 1 + paramInt5)] !=0.0D))
                    {
                        d = paramDouble1 * paramArrayOfDouble2[(i1 - 1 + paramInt5)];
                        i = 1;
                        for (i8 = paramInt1; i8 > 0; i8--)
                        {
                            paramArrayOfDouble3[(i - 1 + paramInt7)] += d * paramArrayOfDouble1[(i - 1 + (n - 1) * paramInt4 + paramInt3)];
                            i++;
                        }
                    }
                    i1 += paramInt6;
                    n++;
                }
            }
            else
            {
                n = 1;
                for (i7 = paramInt2; i7 > 0; i7--)
                {
                    if ((paramArrayOfDouble2[(i1 - 1 + paramInt5)] !=0.0D))
                    {
                        d = paramDouble1 * paramArrayOfDouble2[(i1 - 1 + paramInt5)];
                        m = i4;
                        i = 1;
                        for (i8 = paramInt1; i8 > 0; i8--)
                        {
                            paramArrayOfDouble3[(m - 1 + paramInt7)] += d * paramArrayOfDouble1[(i - 1 + (n - 1) * paramInt4 + paramInt3)];
                            m += paramInt8;
                            i++;
                        }
                    }
                    i1 += paramInt6;
                    n++;
                }
            }
        }
        else
        {
            i2 = i4;
            if ((paramInt6 ==1))
            {
                n = 1;
                for (i7 = paramInt2; i7 > 0; i7--)
                {
                    d = 0.0D;
                    i = 1;
                    for (i8 = paramInt1; i8 > 0; i8--)
                    {
                        d += paramArrayOfDouble1[(i - 1 + (n - 1) * paramInt4 + paramInt3)] * paramArrayOfDouble2[(i - 1 + paramInt5)];
                        i++;
                    }
                    paramArrayOfDouble3[(i2 - 1 + paramInt7)] += paramDouble1 * d;
                    i2 += paramInt8;
                    n++;
                }
            }
            else
            {
                n = 1;
                for (i7 = paramInt2; i7 > 0; i7--)
                {
                    d = 0.0D;
                    k = i3;
                    i = 1;
                    for (i8 = paramInt1; i8 > 0; i8--)
                    {
                        d += paramArrayOfDouble1[(i - 1 + (n - 1) * paramInt4 + paramInt3)] * paramArrayOfDouble2[(k - 1 + paramInt5)];
                        k += paramInt6;
                        i++;
                    }
                    paramArrayOfDouble3[(i2 - 1 + paramInt7)] += paramDouble1 * d;
                    i2 += paramInt8;
                    n++;
                }
            }
        }
    }

    private  int ilaenv(int i, String dgetrf, String s, int paramInt1, int paramInt2, int i1, int i2) {
        return lapack.ilaenv(i,dgetrf,s,paramInt1,paramInt2,i1,i2);
       // return 64; //todo block size - reImplement later
    }
    public  void jdger(int paramInt1, int paramInt2, double paramDouble, double[] paramArrayOfDouble1, int paramInt3, int paramInt4, double[] paramArrayOfDouble2, int paramInt5, int paramInt6, double[] paramArrayOfDouble3, int paramInt7, int paramInt8)
    {
        double d = 0.0D;
        int i = 0;
        // int j = 0;
        int k = 0;
        int m = 0;
        int n = 0;
        int i1 = 0;
      /*  j = 0;
        if ((paramInt1 <0)) {
            j = 1;
        } else if ((paramInt2 <0)) {
            j = 2;
        } else if ((paramInt4 != 0 ^ true)) {
            j = 5;
        } else if ((paramInt6 != 0 ^ true)) {
            j = 7;
        } else if ((paramInt8 >= Math.max(1, paramInt1) ^ true)) {
            j = 9;
        }
        if ((j !=0))
        {
            //Xerbla.xerbla("DGER  ", j);
            return;
        }
        if ((paramInt1 != 0 ? 0 : 1) == 0) {}
        if (((paramInt2 != 0 ? 0 : 1) == 0 ? 0 : 1) == 0) {}
        if (((paramDouble != 0.0D ^ true))) {
            return;
        }
        */
        if ((paramInt6 >0)) {
            n = 1;
        } else {
            n = 1 - (paramInt2 - 1) * paramInt6;
        }
        int i2;
        int i3;
        if ((paramInt4 ==1))
        {
            m = 1;
            for (i2 = paramInt2 ; i2 > 0; i2--)
            {
                if ((paramArrayOfDouble2[(n - 1 + paramInt5)] !=0.0D))
                {
                    d = paramDouble * paramArrayOfDouble2[(n - 1 + paramInt5)];
                    i = 1;
                    for (i3 = paramInt1 ; i3 > 0; i3--)
                    {
                        paramArrayOfDouble3[(i - 1 + (m - 1) * paramInt8 + paramInt7)] += paramArrayOfDouble1[(i - 1 + paramInt3)] * d;
                        i++;
                    }
                }
                n += paramInt6;
                m++;
            }
        }
        else
        {
            if ((paramInt4 >0)) {
                i1 = 1;
            } else {
                i1 = 1 - (paramInt1 - 1) * paramInt4;
            }
            m = 1;
            for (i2 = paramInt2 ; i2 > 0; i2--)
            {
                if ((paramArrayOfDouble2[(n - 1 + paramInt5)] !=0.0D))
                {
                    d = paramDouble * paramArrayOfDouble2[(n - 1 + paramInt5)];
                    k = i1;
                    i = 1;
                    for (i3 = paramInt1 ; i3 > 0; i3--)
                    {
                        paramArrayOfDouble3[(i - 1 + (m - 1) * paramInt8 + paramInt7)] += paramArrayOfDouble1[(k - 1 + paramInt3)] * d;
                        k += paramInt4;
                        i++;
                    }
                }
                n += paramInt6;
                m++;
            }
        }
    }
    public  int idamax(int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3)
    {
        double d = 0.0D;
        int i = 0;
        int j = 0;
        int k = 0;
        k = 0;
        if (((paramInt3 <=0))) {
            return k;
        }
        k = 1;
        if ((paramInt1 ==1)) {
            return k;
        }
        int m;
        if ((paramInt3 ==1))
        {
            d = Math.abs(paramArrayOfDouble[(paramInt2)]);
            i = 2;
            for (m = paramInt1 -1; m > 0; m--)
            {
                if ((Math.abs(paramArrayOfDouble[(i - 1 + paramInt2)]) > d))
                {
                    k = i;
                    d = Math.abs(paramArrayOfDouble[(i - 1 + paramInt2)]);
                }
                i++;
            }
        }
        else
        {
            j = 1;
            d = Math.abs(paramArrayOfDouble[(paramInt2)]);
            j += paramInt3;
            i = 2;
            for (m = paramInt1 -1; m > 0; m--)
            {
                if ((Math.abs(paramArrayOfDouble[(j - 1 + paramInt2)]) > d))
                {
                    k = i;
                    d = Math.abs(paramArrayOfDouble[(j - 1 + paramInt2)]);
                }
                j += paramInt3;
                i++;
            }
        }
        return k;
    }
    public  void jdswap(int paramInt1, double[] paramArrayOfDouble1, int paramInt2, int paramInt3, double[] paramArrayOfDouble2, int paramInt4, int paramInt5)
    {
        double d = 0.0D;
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        int n = 0;
        if ((paramInt1 <= 0)) {
            return;
        }
        int i1;
        if (((paramInt5 ==1) ? 1 : 0) != 0)
        {
            m = paramInt1 % 3;
            if ((m !=0))
            {
                i = 1;
                for (i1 = m ; i1 > 0; i1--)
                {
                    d = paramArrayOfDouble1[(i - 1 + paramInt2)];
                    paramArrayOfDouble1[(i - 1 + paramInt2)] = paramArrayOfDouble2[(i - 1 + paramInt4)];
                    paramArrayOfDouble2[(i - 1 + paramInt4)] = d;
                    i++;
                }
                if ((paramInt1 < 3)) {
                    return;
                }
            }
            n = m + 1;
            i = n;
            for (i1 = floorDiv((paramInt1 - n + 3), 3); i1 > 0; i1--)
            {
                d = paramArrayOfDouble1[(i - 1 + paramInt2)];
                paramArrayOfDouble1[(i - 1 + paramInt2)] = paramArrayOfDouble2[(i - 1 + paramInt4)];
                paramArrayOfDouble2[(i - 1 + paramInt4)] = d;
                d = paramArrayOfDouble1[(i + paramInt2)];
                paramArrayOfDouble1[(i + paramInt2)] = paramArrayOfDouble2[(i + paramInt4)];
                paramArrayOfDouble2[(i + paramInt4)] = d;
                d = paramArrayOfDouble1[(i + 1 + paramInt2)];
                paramArrayOfDouble1[(i + 1 + paramInt2)] = paramArrayOfDouble2[(i + 1 + paramInt4)];
                paramArrayOfDouble2[(i + 1 + paramInt4)] = d;
                i += 3;
            }
        }
        else
        {
            j = 1;
            k = 1;
            if ((paramInt3 <0)) {
                j = (-paramInt1 + 1) * paramInt3 + 1;
            }
            if ((paramInt5 <0)) {
                k = (-paramInt1 + 1) * paramInt5 + 1;
            }
            i = 1;
            for (i1 = paramInt1 ; i1 > 0; i1--)
            {
                d = paramArrayOfDouble1[(j - 1 + paramInt2)];
                paramArrayOfDouble1[(j - 1 + paramInt2)] = paramArrayOfDouble2[(k - 1 + paramInt4)];
                paramArrayOfDouble2[(k - 1 + paramInt4)] = d;
                j += paramInt3;
                k += paramInt5;
                i++;
            }
        }
    }
    public  void jdscal(int paramInt1, double paramDouble, double[] paramArrayOfDouble, int paramInt2, int paramInt3)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;

        int n;
        if ((paramInt3 ==1))
        {
            j = paramInt1 % 5;
            if ((j !=0))
            {
                i = 1;
                for (n = j ; n > 0; n--)
                {
                    // System.out.println("*");
                    paramArrayOfDouble[(i - 1 + paramInt2)] = (paramDouble * paramArrayOfDouble[(i - 1 + paramInt2)]);
                    i++;
                }
                if ((paramInt1 < 5)) {
                    return;
                }
            }
            k = j + 1;
            i = k;
            for (n = floorDiv((paramInt1 - k + 5), 5); n > 0; n--)
            {
                // System.out.println(n);
                paramArrayOfDouble[(i - 1 + paramInt2)] = (paramDouble * paramArrayOfDouble[(i - 1 + paramInt2)]);
                paramArrayOfDouble[(i + paramInt2)] = (paramDouble * paramArrayOfDouble[(i + paramInt2)]);
                paramArrayOfDouble[(i + 1 + paramInt2)] = (paramDouble * paramArrayOfDouble[(i + 1 + paramInt2)]);
                paramArrayOfDouble[(i + 2 + paramInt2)] = (paramDouble * paramArrayOfDouble[(i + 2 + paramInt2)]);
                paramArrayOfDouble[(i + 3 + paramInt2)] = (paramDouble * paramArrayOfDouble[(i + 3 + paramInt2)]);
                i += 5;
            }
        } else {
            m = paramInt1 * paramInt3;
            i = 1;
            for (n = floorDiv((m - 1 + paramInt3), paramInt3); n > 0; n--)
            {
                paramArrayOfDouble[(i - 1 + paramInt2)] = (paramDouble * paramArrayOfDouble[(i - 1 + paramInt2)]);
                i += paramInt3;
            }
        }
    }
    public  void jdgetf2(int paramInt1, int paramInt2, double[] paramArrayOfDouble, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5, int[] info)
    {
        double d = 0.0D;
        int i = 0;
        int j = 0;
        int k = 0;
        info[0] = 0;
        if ((paramInt1 <0)) {
            info[0] = -1;
        } else if ((paramInt2 <0)) {
            info[0] = -2;
        } else if ((paramInt4 < Math.max(1, paramInt1))) {
            info[0] = -4;
        }
        if ((info[0] !=0))
        {
            //Xerbla.xerbla("DGETF2", -info);
            return;
        }
        if (((paramInt2 == 0))) {
            return;
        }
        d = PrimitiveHelper.DOUBLE_MIN_VALUE(); //dlamch("S"); //toDo implement later
        j = 1;
        for (int m = Math.min(paramInt1, paramInt2) ; m > 0; m--) {
            k = j - 1 + idamax(paramInt1 - j + 1, paramArrayOfDouble, j - 1 + (j - 1) * paramInt4 + paramInt3, 1);
            paramArrayOfInt[(j - 1 + paramInt5)] = k;
            if ((paramArrayOfDouble[(k - 1 + (j - 1) * paramInt4 + paramInt3)] !=0.0D))
            {
                if ((k != j)) {
                    jdswap(paramInt2, paramArrayOfDouble, j - 1 + paramInt3, paramInt4, paramArrayOfDouble, k - 1 + paramInt3, paramInt4);
                }
                if ((j < paramInt1)) {
                    if ((Math.abs(paramArrayOfDouble[(j - 1 + (j - 1) * paramInt4 + paramInt3)]) >= d))
                    {
                        jdscal(paramInt1 - j, 1.0D / paramArrayOfDouble[(j - 1 + (j - 1) * paramInt4 + paramInt3)], paramArrayOfDouble, j + (j - 1) * paramInt4 + paramInt3, 1);
                    }
                    else
                    {
                        i = 1;
                        for (int n = paramInt1 - j ; n > 0; n--)
                        {
                            paramArrayOfDouble[(j + i - 1 + (j - 1) * paramInt4 + paramInt3)] /= paramArrayOfDouble[(j - 1 + (j - 1) * paramInt4 + paramInt3)];
                            i++;
                        }
                    }
                }
            }
            else if ((info[0] == 0))
            {
                info[0] = j;
            }
            if ((j < Math.min(paramInt1, paramInt2))) {
                jdger(paramInt1 - j, paramInt2 - j, -1.0D, paramArrayOfDouble, j + (j - 1) * paramInt4 + paramInt3, 1, paramArrayOfDouble, j - 1 + (j ) * paramInt4 + paramInt3, paramInt4, paramArrayOfDouble, j + (j ) * paramInt4 + paramInt3, paramInt4);
            }
            j++;
        }
    }
    public  void jdlaswp(int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt, int paramInt6, int paramInt7)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        int n = 0;
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        double d = 0.0D;
        if ((paramInt7 >0))
        {
            i2 = paramInt4;
            j = paramInt4;
            k = paramInt5;
            m = 1;
        }
        else if ((paramInt7 <0))
        {
            i2 = 1 + (1 - paramInt5) * paramInt7;
            j = paramInt5;
            k = paramInt4;
            m = -1;
        }
        else
        {
            return;
        }
        i5 = floorDiv(paramInt1, 32) * 32;
        int i6;
        int i7;
        if ((i5 !=0))
        {
            i3 = 1;
            for (i6 = floorDiv((i5 - 1 + 32), 32); i6 > 0; i6--)
            {
                i1 = i2;
                i = j;
                for (i7 = floorDiv((k - j + m), m); i7 > 0; i7--)
                {
                    n = paramArrayOfInt[(i1 - 1 + paramInt6)];
                    if ((n != i))
                    {
                        i4 = i3;
                        for (int i8 = i3 + 31 - i3 + 1; i8 > 0; i8--)
                        {
                            d = paramArrayOfDouble[(i - 1 + (i4 - 1) * paramInt3 + paramInt2)];
                            paramArrayOfDouble[(i - 1 + (i4 - 1) * paramInt3 + paramInt2)] = paramArrayOfDouble[(n - 1 + (i4 - 1) * paramInt3 + paramInt2)];
                            paramArrayOfDouble[(n - 1 + (i4 - 1) * paramInt3 + paramInt2)] = d;
                            i4++;
                        }
                    }
                    i1 += paramInt7;
                    i += m;
                }
                i3 += 32;
            }
        }
        if ((i5 != paramInt1))
        {
            i5++;
            i1 = i2;
            i = j;
            for (i6 = floorDiv((k - j + m), m); i6 > 0; i6--)
            {
                n = paramArrayOfInt[(i1 - 1 + paramInt6)];
                if ((n != i))
                {
                    i4 = i5;
                    for (i7 = paramInt1 - i5 + 1; i7 > 0; i7--)
                    {
                        d = paramArrayOfDouble[(i - 1 + (i4 - 1) * paramInt3 + paramInt2)];
                        paramArrayOfDouble[(i - 1 + (i4 - 1) * paramInt3 + paramInt2)] = paramArrayOfDouble[(n - 1 + (i4 - 1) * paramInt3 + paramInt2)];
                        paramArrayOfDouble[(n - 1 + (i4 - 1) * paramInt3 + paramInt2)] = d;
                        i4++;
                    }
                }
                i1 += paramInt7;
                i += m;
            }
        }
    }
    public  void jdtrsm(KBlasSideType paramString1, KBlasOrientationType paramString2, KBlasTransposeType paramString3, KBlasUnitType paramString4, int paramInt1, int paramInt2, double paramDouble, double[] paramArrayOfDouble1, int paramInt3, int paramInt4, double[] paramArrayOfDouble2, int paramInt5, int paramInt6)
    {
        double d = 0.0D;
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        int n = 0;
        boolean bool1 = false;
        boolean bool2 = false;
        boolean bool3 = false;
        bool1 =paramString1.equals(KBlasSideType.LEFT);
        if (bool1) {
            n = paramInt1;
        } else {
            n = paramInt2;
        }
        bool2 = paramString4.equals(KBlasUnitType.NONUNIT);
        bool3 = paramString2.equals(KBlasOrientationType.UPPER);
        /*j = 0;
        if ((((!bool1)) && ((!paramString1.equals(KBlasSideType.RIGHT))) ? 1 : 0) != 0)
        {
            j = 1;
        }
        else if ((((!bool3)) && ((!paramString2.equals(KBlasOrientationType.LOWER))) ? 1 : 0) != 0)
        {
            j = 2;
        }
        else
        {
            if (((!paramString3.equals(KBlasTransposeType.CONJUGATE)) ? 1 : 0) != 0) {
                j = 3;
            } else if ((((!paramString4.equals(KBlasUnitType.UNIT))) && ((!paramString4.equals(KBlasUnitType.NONUNIT))) ? 1 : 0) != 0) {
                j = 4;
            } else if ((paramInt1 <0)) {
                j = 5;
            } else if ((paramInt2 <0)) {
                j = 6;
            } else if ((paramInt4 >= Math.max(1, n) ^ true)) {
                j = 9;
            } else if ((paramInt6 >= Math.max(1, paramInt1) ^ true)) {
                j = 11;
            }
        }
        if ((j !=0))
        {
            //Xerbla.xerbla("DTRSM ", j);
            return;
        }
        if (((paramInt2 != 0 ^ true))) {
            return;
        }*/
        int i1;
        int i2;
        if ((paramDouble ==0))
        {
            k = 1;
            for (i1 = paramInt2 ; i1 > 0; i1--)
            {
                i = 1;
                for (i2 = paramInt1 ; i2 > 0; i2--)
                {
                    paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = 0.0D;
                    i++;
                }
                k++;
            }
            return;
        }
        int i3;
        if (bool1)
        {
            if (paramString3.equals(KBlasTransposeType.NOTRANSPOSE))
            {
                if (bool3)
                {
                    k = 1;
                    for (i1 = paramInt2 ; i1 > 0; i1--)
                    {
                        if ((paramDouble !=1.0D))
                        {
                            i = 1;
                            for (i2 = paramInt1 ; i2 > 0; i2--)
                            {
                                paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = (paramDouble * paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)]);
                                i++;
                            }
                        }
                        m = paramInt1;
                        for (i2 = paramInt1 ; i2 > 0; i2--)
                        {
                            if ((paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)] !=0.0D))
                            {
                                if (bool2) {
                                    paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)] /= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt4 + paramInt3)];
                                }
                                i = 1;
                                for (i3 = m - 1 ; i3 > 0; i3--)
                                {
                                    paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] -= paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)] * paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt4 + paramInt3)];
                                    i++;
                                }
                            }
                            m += -1;
                        }
                        k++;
                    }
                }
                else
                {
                    k = 1;
                    for (i1 = paramInt2 ; i1 > 0; i1--)
                    {
                        if ((paramDouble !=1.0D))
                        {
                            i = 1;
                            for (i2 = paramInt1 ; i2 > 0; i2--)
                            {
                                paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = (paramDouble * paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)]);
                                i++;
                            }
                        }
                        m = 1;
                        for (i2 = paramInt1 ; i2 > 0; i2--)
                        {
                            if ((paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)] !=0.0D))
                            {
                                if (bool2) {
                                    paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)] /= paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt4 + paramInt3)];
                                }
                                i = m + 1;
                                for (i3 = paramInt1 - (m + 1) + 1; i3 > 0; i3--)
                                {
                                    paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] -= paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)] * paramArrayOfDouble1[(i - 1 + (m - 1) * paramInt4 + paramInt3)];
                                    i++;
                                }
                            }
                            m++;
                        }
                        k++;
                    }
                }
            }
            else if (bool3)
            {
                k = 1;
                for (i1 = paramInt2 ; i1 > 0; i1--)
                {
                    i = 1;
                    for (i2 = paramInt1 ; i2 > 0; i2--)
                    {
                        d = paramDouble * paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)];
                        m = 1;
                        for (i3 = i - 1 ; i3 > 0; i3--)
                        {
                            d -= paramArrayOfDouble1[(m - 1 + (i - 1) * paramInt4 + paramInt3)] * paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)];
                            m++;
                        }
                        if (bool2) {
                            d /= paramArrayOfDouble1[(i - 1 + (i - 1) * paramInt4 + paramInt3)];
                        }
                        paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = d;
                        i++;
                    }
                    k++;
                }
            }
            else
            {
                k = 1;
                for (i1 = paramInt2 ; i1 > 0; i1--)
                {
                    i = paramInt1;
                    for (i2 = paramInt1 ; i2 > 0; i2--)
                    {
                        d = paramDouble * paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)];
                        m = i + 1;
                        for (i3 = paramInt1 - i ; i3 > 0; i3--)
                        {
                            d -= paramArrayOfDouble1[(m - 1 + (i - 1) * paramInt4 + paramInt3)] * paramArrayOfDouble2[(m - 1 + (k - 1) * paramInt6 + paramInt5)];
                            m++;
                        }
                        if (bool2) {
                            d /= paramArrayOfDouble1[(i - 1 + (i - 1) * paramInt4 + paramInt3)];
                        }
                        paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = d;
                        i += -1;
                    }
                    k++;
                }
            }
        }
        else if (paramString3.equals(KBlasTransposeType.NOTRANSPOSE))
        {
            if (bool3)
            {
                k = 1;
                for (i1 = paramInt2 ; i1 > 0; i1--)
                {
                    if ((paramDouble !=1.0D))
                    {
                        i = 1;
                        for (i2 = paramInt1 ; i2 > 0; i2--)
                        {
                            paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = (paramDouble * paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)]);
                            i++;
                        }
                    }
                    m = 1;
                    for (i2 = k - 1 ; i2 > 0; i2--)
                    {
                        if ((paramArrayOfDouble1[(m - 1 + (k - 1) * paramInt4 + paramInt3)] !=0.0D))
                        {
                            i = 1;
                            for (i3 = paramInt1 ; i3 > 0; i3--)
                            {
                                paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] -= paramArrayOfDouble1[(m - 1 + (k - 1) * paramInt4 + paramInt3)] * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)];
                                i++;
                            }
                        }
                        m++;
                    }
                    if (bool2)
                    {
                        d = 1.0D / paramArrayOfDouble1[(k - 1 + (k - 1) * paramInt4 + paramInt3)];
                        i = 1;
                        for (i2 = paramInt1 ; i2 > 0; i2--)
                        {
                            paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = (d * paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)]);
                            i++;
                        }
                    }
                    k++;
                }
            }
            else
            {
                k = paramInt2;
                for (i1 = paramInt2; i1 > 0; i1--)
                {
                    if ((paramDouble !=1.0D))
                    {
                        i = 1;
                        for (i2 = paramInt1 ; i2 > 0; i2--)
                        {
                            paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = (paramDouble * paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)]);
                            i++;
                        }
                    }
                    m = k + 1;
                    for (i2 = paramInt2 - (k + 1) + 1; i2 > 0; i2--)
                    {
                        if ((paramArrayOfDouble1[(m - 1 + (k - 1) * paramInt4 + paramInt3)] !=0.0D))
                        {
                            i = 1;
                            for (i3 = paramInt1 ; i3 > 0; i3--)
                            {
                                paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] -= paramArrayOfDouble1[(m - 1 + (k - 1) * paramInt4 + paramInt3)] * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)];
                                i++;
                            }
                        }
                        m++;
                    }
                    if (bool2)
                    {
                        d = 1.0D / paramArrayOfDouble1[(k - 1 + (k - 1) * paramInt4 + paramInt3)];
                        i = 1;
                        for (i2 = paramInt1 ; i2 > 0; i2--)
                        {
                            paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] = (d * paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)]);
                            i++;
                        }
                    }
                    k += -1;
                }
            }
        }
        else if (bool3)
        {
            m = paramInt2;
            for (i1 = paramInt2; i1 > 0; i1--)
            {
                if (bool2)
                {
                    d = 1.0D / paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt4 + paramInt3)];
                    i = 1;
                    for (i2 = paramInt1 ; i2 > 0; i2--)
                    {
                        paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)] = (d * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)]);
                        i++;
                    }
                }
                k = 1;
                for (i2 = m - 1 ; i2 > 0; i2--)
                {
                    if ((paramArrayOfDouble1[(k - 1 + (m - 1) * paramInt4 + paramInt3)] !=0.0D))
                    {
                        d = paramArrayOfDouble1[(k - 1 + (m - 1) * paramInt4 + paramInt3)];
                        i = 1;
                        for (i3 = paramInt1 ; i3 > 0; i3--)
                        {
                            paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] -= d * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)];
                            i++;
                        }
                    }
                    k++;
                }
                if ((paramDouble !=1.0D))
                {
                    i = 1;
                    for (i2 = paramInt1 ; i2 > 0; i2--)
                    {
                        paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)] = (paramDouble * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)]);
                        i++;
                    }
                }
                m += -1;
            }
        }
        else
        {
            m = 1;
            for (i1 = paramInt2 ; i1 > 0; i1--)
            {
                if (bool2)
                {
                    d = 1.0D / paramArrayOfDouble1[(m - 1 + (m - 1) * paramInt4 + paramInt3)];
                    i = 1;
                    for (i2 = paramInt1 ; i2 > 0; i2--)
                    {
                        paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)] = (d * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)]);
                        i++;
                    }
                }
                k = m + 1;
                for (i2 = paramInt2 - (m + 1) + 1; i2 > 0; i2--)
                {
                    if ((paramArrayOfDouble1[(k - 1 + (m - 1) * paramInt4 + paramInt3)] !=0.0D))
                    {
                        d = paramArrayOfDouble1[(k - 1 + (m - 1) * paramInt4 + paramInt3)];
                        i = 1;
                        for (i3 = paramInt1 ; i3 > 0; i3--)
                        {
                            paramArrayOfDouble2[(i - 1 + (k - 1) * paramInt6 + paramInt5)] -= d * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)];
                            i++;
                        }
                    }
                    k++;
                }
                if ((paramDouble !=1.0D))
                {
                    i = 1;
                    for (i2 = paramInt1 ; i2 > 0; i2--)
                    {
                        paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)] = (paramDouble * paramArrayOfDouble2[(i - 1 + (m - 1) * paramInt6 + paramInt5)]);
                        i++;
                    }
                }
                m++;
            }
        }
    }

    /**
     * @native ts
     * return Math.floor(x/y);
     */
    private  int floorDiv(int x, int y){
        return Math.floorDiv(x,y);
    }

    private static final String TRANSPOSE_TYPE_CONJUCATE = "c";

    private static final String TRANSPOSE_TYPE_NOTRANSPOSE = "n";

    private static final String TRANSPOSE_TYPE_TRANSPOSE = "t";

    private static String transTypeToChar(KBlasTransposeType type) {
        if (type.equals(KBlasTransposeType.CONJUGATE)) {
            return TRANSPOSE_TYPE_CONJUCATE;
        } else if (type.equals(KBlasTransposeType.NOTRANSPOSE)) {
            return TRANSPOSE_TYPE_NOTRANSPOSE;
        } else if (type.equals(KBlasTransposeType.TRANSPOSE)) {
            return TRANSPOSE_TYPE_TRANSPOSE;
        }
        return null;
    }
}
