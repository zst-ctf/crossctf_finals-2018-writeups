package com.company;

import java.math.BigInteger;

public class Main {

    public static class Status
    {
        private BigInteger curr;
        private BigInteger f;
        public StringBuilder flagBuilder = new StringBuilder();
        private BigInteger fmax;
        int level = 0;
        private BigInteger max;
        private BigInteger prod;

        Status()
        {
            upLevel();
        }

        private String extract()
        {
            final BigInteger localBigInteger = BigInteger.valueOf(1000L);
            speedUp();

            return this.prod.mod(localBigInteger).add(localBigInteger).toString().substring(1);
        }

        /**private BigInteger fastExpo(BigInteger X, BigInteger Y)
        {

            BigInteger _B = BigInteger.ONE;
            // for the number of bits in Y
            //
            while (Y.compareTo(BigInteger.ONE) != 0)
            {
                //BigInteger _A = _B;
                if (Y.and(BigInteger.ONE).equals(BigInteger.ONE)) {
                    _B = (_B).multiply(X);
                }
                //_B = _A;
                X = X.multiply(X); // x^2
                Y = Y.shiftRight(1);


            }
            return _B.multiply(X);
        }**/

        private static BigInteger fastExpo(BigInteger X, BigInteger Y)
        {
            System.out.println("Y.intValue(): " + Y.intValue());
            return X.pow(Y.intValue());
            //return _B.multiply(X);
        }

        private void upF()
        {
            this.fmax = fastExpo(BigInteger.valueOf(5L), this.curr).add(BigInteger.ONE);
            System.out.println("fmax=" + fmax);
            this.f = BigInteger.ONE;
        }

        private void upLevel()
        {
            this.level += 1;
            this.max = BigInteger.ONE.shiftLeft(this.level).add(BigInteger.ONE); // 2^ level + 1
            this.curr = BigInteger.ONE;
            this.prod = BigInteger.ONE;
            upF();
        }


        /*
        public static BigInteger factorial(BigInteger n) {
            BigInteger result = BigInteger.ONE;

            while (!n.equals(BigInteger.ZERO)) {
                result = result.multiply(n.mod(BigInteger.valueOf(1000L)));
                System.out.println("Factorial" + result);
                n = n.subtract(BigInteger.ONE);
            }

            return result;
        }
        public void crank(double paramDouble)
        {
            paramDouble = Math.pow(paramDouble, this.level);
            int i = 0;
            while (i < paramDouble)
            {
                // f_total
                BigInteger ftotal = fastExpo(BigInteger.valueOf(5L), this.max).add(BigInteger.ONE);
                this.prod = this.prod.multiply(factorial(ftotal));

                if (this.level == 13) {
                    System.out.println("Success");
                    return;
                }
                this.flagBuilder.append(extract());
                upLevel();

                i += 1;
            }
        }**/

        public void crank(double paramDouble)
        {
            paramDouble = Math.pow(paramDouble, this.level);
            int i = 0;
            while (i < paramDouble)
            {
                if (this.max.compareTo(this.curr) == 0)
                {
                    if (this.level == 13) {
                        return;
                    }
                    this.flagBuilder.append(extract());
                    upLevel();
                }
                else if (this.f.compareTo(this.fmax) == 0)
                {
                    this.curr = this.curr.add(BigInteger.ONE);
                    upF();
                }
                else
                {
                    this.prod = speedUpNumber(
                            this.prod.multiply(
                                speedUpNumber(this.f, 10000L)
                            ), 1000000000000000L
                    );
                    this.f = this.f.add(BigInteger.ONE);
                }
                i += 1;
            }
        }

        public void crankFast() {
            if (this.max.compareTo(this.curr) == 0) {
                if (this.level == 13) {
                    return;
                }
                this.flagBuilder.append(extract());
                upLevel();
            } else if (this.f.compareTo(this.fmax) == 0) {
                this.curr = this.curr.add(BigInteger.ONE);
                upF();
            } else {
                this.prod = speedUpNumber(
                        this.prod.multiply(
                                speedUpNumber(this.f, 10000L)
                        ), 1000000L
                );
                this.f = this.f.add(BigInteger.ONE);
            }
        }



        /*
        public void crank(double paramDouble)
        {
            paramDouble = Math.pow(paramDouble, this.level);
            int i = 0;
            while (i < paramDouble)
            {
                if (this.max.compareTo(this.curr) == 0)
                {
                    if (this.level == 13) {
                        return;
                    }
                    this.flagBuilder.append(extract());
                    upLevel();
                }
                else if (this.f.compareTo(this.fmax) == 0)
                {
                    this.curr = this.curr.add(BigInteger.ONE);
                    upF();
                }
                else
                {
                    //System.out.println("prod="+prod);
                    System.out.println("f="+f);
                    this.prod = this.prod.multiply(this.f);
                    this.f = this.f.add(BigInteger.ONE);
                }
                i += 1;
            }
        }*/


        public BigInteger speedUpNumber(BigInteger input, long howmuch)
        {
            while (input.mod(BigInteger.TEN).equals(BigInteger.ZERO)) {
                input = input.divide(BigInteger.TEN);
            }
            input = input.mod(BigInteger.valueOf(howmuch));
            return input;
            // .mod(BigInteger.valueOf(100000L)
            //
        }

        public void crank3(double paramDouble)
        {
            paramDouble = Math.pow(paramDouble, this.level);
            int i = 0;
            while (i < paramDouble)
            {
                if (this.max.compareTo(this.curr) == 0)
                {
                    if (this.level == 13) {
                        return;
                    }
                    this.flagBuilder.append(extract());
                    upLevel();
                }
                else if (this.f.compareTo(this.fmax) == 0)
                {
                    this.curr = this.curr.add(BigInteger.ONE);
                    upF();
                }
                else
                {
                    //
                    //System.out.println("f="+f);
                    this.prod = this.prod.multiply(speedUpNumber(this.f, 10000L));

                    // optimise
                    speedUp();

                    //System.out.println("prod="+prod);
                    this.f = this.f.add(BigInteger.ONE);
                }
                i += 1;
            }
        }


        public String flag()
        {
            return this.flagBuilder.toString();
        }

        public int level()
        {
            return this.level;
        }

        public String percent()
        {
            double d1 = BigInteger.valueOf(10000L).divide(this.max.subtract(BigInteger.ONE)).doubleValue();
            double d2 = this.curr.doubleValue();
            return String.format("%.2f",
                    Math.min(
                            100.0D,
                            (BigInteger.valueOf(10000L).multiply(this.f).divide(this.fmax).doubleValue() / 10000.0D + (d2 - 1.0D)) * d1 / 100.0D
                    )
            );
        }

        public void speedUp()
        {
            //System.out.println("speed="+prod);
            while (this.prod.mod(BigInteger.TEN).equals(BigInteger.ZERO)) {
                this.prod = this.prod.divide(BigInteger.TEN);
            }
            //this.prod = this.prod.mod(BigInteger.valueOf(10000000000L));
        }
    }


    public static void main(String[] args) {
        Status s = new Status();
        while (true) {
            /*int l = s.level;
            while (l == s.level) {
                s.crank(3.8D);
            }*/
            s.crank(3.8D);
            //s.crankFast();
            //s.speedUp();
            System.out.print("Level: " + s.level);
            System.out.print(" / ");

            System.out.print("Percent: " + s.percent());
            System.out.print(" / ");

            System.out.println("CrossCTF{" + s.flagBuilder.toString() + "}");
        }
        /*for (int i = 0; i < 14; i++){

        }*/
        // write your code here
    }
}
//
//public class Main {
//
//    public static class Status
//    {
//        long curr;
//        long f;
//        public StringBuilder flagBuilder = new StringBuilder();
//        long fmax;
//        int level = 0;
//        long max;
//        long prod;
//
//        Status()
//        {
//            upLevel();
//        }
//
//        /*private String extract()
//        {
//            BigInteger localBigInteger = BigInteger.valueOf(1000L);
//            speedUp();
//            return (this.prod % 1000L) + "";
//            // + 1000L) .toString().substring(1);
//        }*/
//        private String extract()
//        {
//            speedUp();
//            return (((this.prod % 1000L) + (1000L)) + "").substring(1);
//        }
//
//
//        /*
//        private BigInteger fastExpo(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
//        {
//            BigInteger localObject2;
//            BigInteger localObject1;
//            for (localObject1 = BigInteger.ONE; paramBigInteger2.compareTo(BigInteger.ONE) != 0; localObject1 = localObject2)
//            {
//                localObject2 = localObject1;
//                if (paramBigInteger2.and(BigInteger.ONE).equals(BigInteger.ONE)) {
//                    localObject2 = (localObject1).multiply(paramBigInteger1);
//                }
//                paramBigInteger1 = paramBigInteger1.multiply(paramBigInteger1);
//                paramBigInteger2 = paramBigInteger2.shiftRight(1);
//            }
//            return localObject1.multiply(paramBigInteger1);
//        }*/
//
//
//
//        /**
//        private long fastExpo(long paramBigInteger1, long paramBigInteger2)
//        {
//            long localObject2;
//            long localObject1;
//            for (localObject1 = 1; paramBigInteger2 != 1; localObject1 = localObject2)
//            {
//                localObject2 = localObject1;
//                if ((paramBigInteger2 & 0x1) == 1) {
//                    localObject2 = (localObject1) * (paramBigInteger1);
//                }
//                paramBigInteger1 = paramBigInteger1 * (paramBigInteger1);
//                paramBigInteger2 = paramBigInteger2 >> 1;
//            }
//            return localObject1 * (paramBigInteger1);
//        }**/
//
//        private long fastExpo(long a, long b)
//        {
//            long localObject1 = 1;
//
//            while (b != 1)
//            {
//                //System.out.println("b != 1: "+ b);
//
//                long localObject2 = localObject1;
//                if ((b & 0x1) == 1) { // if b is odd
//                    localObject2 = (localObject1) * (a);
//                }
//                a = a * (a); // sq of a
//                b >>= 1;
//
//                localObject1 = localObject2;
//            }
//            return localObject1 * (a);
//        }
//
//
//        private void upF()
//        {
//            this.fmax = fastExpo((5L), this.curr) + 1;
//            this.f = 1;
//        }
//        /*
//        private void upF()
//        {
//            this.fmax = fastExpo(BigInteger.valueOf(5L), this.curr).add(BigInteger.ONE);
//            System.out.println("fmax=" + fmax);
//            this.f = BigInteger.ONE;
//        }
//         */
//
//        /*
//        private void upLevel()
//        {
//            this.level += 1;
//            this.max = BigInteger.ONE.shiftLeft(this.level).add(BigInteger.ONE);
//            this.curr = BigInteger.ONE;
//            this.prod = BigInteger.ONE;
//            upF();
//        }
//         */
//        private void upLevel()
//        {
//            this.level += 1;
//            this.max = (1 << (this.level)) + 1;
//            this.curr = 1;
//            this.prod = 1;
//            upF();
//        }
//
//        public void crank(double paramDouble)
//        {
//            //paramDouble = Math.pow(paramDouble, this.level);
//            long compare = (long) Math.pow(paramDouble, this.level);
//            int i = 0;
//            //while (i < paramDouble)
//            while (i < compare)
//            {
//                if (this.max == (this.curr))
//                {
//                    if (this.level == 13) {
//                        return;
//                    }
//                    this.flagBuilder.append(extract());
//                    upLevel();
//                }
//                else if (this.f == (this.fmax))
//                {
//                    this.curr++;
//                    upF();
//                }
//                else
//                {
//                    //System.out.println("else branch: "+ f);
//                    this.prod = this.prod * (this.f);
//                    this.f++;
//                }
//                i += 1;
//            }
//        }
//        /*
//        public void crank(double paramDouble)
//        {
//            paramDouble = Math.pow(paramDouble, this.level);
//            int i = 0;
//            while (i < paramDouble)
//            {
//                if (this.max.compareTo(this.curr) == 0)
//                {
//                    if (this.level == 13) {
//                        return;
//                    }
//                    this.flagBuilder.append(extract());
//                    upLevel();
//                }
//                else if (this.f.compareTo(this.fmax) == 0)
//                {
//                    this.curr = this.curr.add(BigInteger.ONE);
//                    upF();
//                }
//                else
//                {
//                    this.prod = this.prod.multiply(this.f);
//                    this.f = this.f.add(BigInteger.ONE);
//                }
//                i += 1;
//            }
//        }*/
//
//
//        public String flag()
//        {
//            return this.flagBuilder.toString();
//        }
//
//        public int level()
//        {
//            return this.level;
//        }
//
//        public String percent()
//        {
//            double d1 = 10000L / (this.max - 1);
//            double d2 = this.curr;
//            return String.format("%.2f", Math.min(100.0D, ((10000.0) * (this.f) / (this.fmax) / 10000.0D + (d2 - 1.0D)) * d1 / 100.0D));
//        }
//
//        public void speedUp()
//        {
//            while ((this.prod % 10) == 0) {
//                System.out.println("speedUp "+ prod);
//
//                this.prod = this.prod / 10;
//            }
//        }
//    }
//
//
//    public static void main(String[] args) {
//        Status s = new Status();
//        while (true) {
//            //s.crank(3.8D);
//            //s.speedUp();
//            int l = s.level;
//            while (l == s.level) {
//                s.crank(3.8D);
//            }
//            s.speedUp();
//            System.out.print("Level: " + s.level);
//            System.out.print(" / ");
//
//            System.out.print("Percent: " + s.percent());
//            System.out.print(" / ");
//
//            System.out.println("CrossCTF{" + s.flagBuilder.toString() + "}");
//        }
//        // write your code here
//    }
//}
