import java.math.BigInteger;

public class MyClass {
    private static BigInteger fastExpo(BigInteger X, BigInteger Y)
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
    }
    
    private static BigInteger fastExpo2(BigInteger X, BigInteger Y)
    {
        return X.pow(Y.intValue());
        //return _B.multiply(X);
    }
        
    public static void main(String args[]) {

        System.out.println("fastExpo() =" + fastExpo(BigInteger.valueOf(10L), BigInteger.valueOf(0xA)).longValue());
        System.out.println("fastExpo2() =" + fastExpo2(BigInteger.valueOf(10L), BigInteger.valueOf(0xA)).longValue());
    }
}
