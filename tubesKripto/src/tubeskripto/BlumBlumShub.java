/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubeskripto;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Sei
 * Kode direferensi dari 
 */
public class BlumBlumShub {
    private static final BigInteger two = BigInteger.valueOf(2L);
    private static final BigInteger three = BigInteger.valueOf(3L);
    private static final BigInteger four = BigInteger.valueOf(4L);
    
    private BigInteger n;
    private BigInteger state;
    
    private static BigInteger getPrima(int bits, Random rand) {
	BigInteger p;
	while (true) {
	    p = new BigInteger(bits, 100, rand);
	    if (p.mod(four).equals(three))
		break;
            
	}
	return p;
    }
    
    public static BigInteger generateN(int bits, Random rand) {
	BigInteger p = getPrima(bits/2, rand);
	BigInteger q = getPrima(bits/2, rand);

	// make sure p != q (almost always true, but just in case, check)
	while (p.equals(q)) {
	    q = getPrima(bits, rand);
	}
	return p.multiply(q);
    }
    
    public BlumBlumShub(int bits) {
	this(bits, new Random());
    }
    
    public BlumBlumShub(int bits, Random rand) {
	this(generateN(bits, rand));
    }
    
    public BlumBlumShub(BigInteger n) {
	this(n, SecureRandom.getSeed(n.bitLength() / 8));
    }
    
    public BlumBlumShub(BigInteger n, byte[] seed) {
	this.n = n;
	setSeed(seed);
    }
    
    public void setSeed(byte[] seedBytes) {
	// ADD: use hardwired default for n
	BigInteger seed = new BigInteger(1, seedBytes);
	state = seed.mod(n);
    }
    
    public int next(int numBits) {
	// TODO: find out how many LSB one can extract per cycle.
	//   it is more than one.
	int result = 0;
	for (int i = numBits; i != 0; --i) {
	    state = state.modPow(two, n);
	    result = (result << 1) | (state.testBit(0) == true ? 1 : 0);
	}
	return result;
    }

}
