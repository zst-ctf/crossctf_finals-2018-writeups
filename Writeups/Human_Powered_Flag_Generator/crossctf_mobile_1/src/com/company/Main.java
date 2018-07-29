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
            BigInteger localBigInteger = BigInteger.valueOf(1000L);
            speedUp();
            // if (level == 2) System.exit(1);
            return this.prod.mod(localBigInteger).add(localBigInteger).toString().substring(1);
        }

        private BigInteger fastExpo(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
        {
            BigInteger localObject2;
            BigInteger localObject1;
            for (localObject1 = BigInteger.ONE; paramBigInteger2.compareTo(BigInteger.ONE) != 0; localObject1 = localObject2)
            {
                localObject2 = localObject1;
                if (paramBigInteger2.and(BigInteger.ONE).equals(BigInteger.ONE)) {
                    localObject2 = (localObject1).multiply(paramBigInteger1);
                }
                paramBigInteger1 = paramBigInteger1.multiply(paramBigInteger1);
                paramBigInteger2 = paramBigInteger2.shiftRight(1);
            }
            return localObject1.multiply(paramBigInteger1);
        }

        private void upF()
        {
            this.fmax = fastExpo(BigInteger.valueOf(5L), this.curr).add(BigInteger.ONE);
            //System.out.println("fmax=" + fmax);
            this.f = BigInteger.ONE;
        }

        private void upLevel()
        {
            this.level += 1;
            this.max = BigInteger.ONE.shiftLeft(this.level).add(BigInteger.ONE);
            this.curr = BigInteger.ONE;
            this.prod = BigInteger.ONE;
            upF();
        }

        public void crank(double paramDouble)
        {
            paramDouble = Math.pow(paramDouble, this.level);
            int i = 0;
            while (i < paramDouble)
            {
                System.out.printf("Level=%d | f=%d | fmax=%d | prod=%d\n", level, f, fmax, prod);
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
                else // FACTORIAL
                {
                    this.prod = this.prod.multiply(this.f);
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
            return String.format("%.2f", Math.min(100.0D, (BigInteger.valueOf(10000L).multiply(this.f).divide(this.fmax).doubleValue() / 10000.0D + (d2 - 1.0D)) * d1 / 100.0D));
        }

        public void speedUp()
        {
            while (this.prod.mod(BigInteger.TEN).equals(BigInteger.ZERO)) {
                //System.out.println("speedUp:"+ prod);
                this.prod = this.prod.divide(BigInteger.TEN);
            }
        }
    }


    public static void main(String[] args) {
        Status s = new Status();
        while (true) {
            int l = s.level;
            /*while (l == s.level) {
                s.crank(3.8D);
            }*/
            s.crank(3.8D);
            //s.speedUp();
            System.out.print("Level: " + s.level);
            System.out.print(" / ");

            System.out.print("Percent: " + s.percent());
            System.out.print(" / ");

            System.out.println("CrossCTF{" + s.flagBuilder.toString() + "}");
        }
	// write your code here
    }
}
