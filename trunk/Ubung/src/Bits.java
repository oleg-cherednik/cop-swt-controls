
public class Bits
{
	public static int getSetBitsNumber(byte num)
	{
        num = (byte)((num & (byte)0x55) + ((num >> 1) & (byte)0x55));
        num = (byte)((num & 0x33) + ((num >> 2) & 0x33));
        
        return (num & 0x0f) + ((num >> 4) & 0x0f);
	}
	
	public static void main(String[] args)
	{
		byte num = 114;
		int tmp = 0;
		
		tmp = getSetBitsNumber(num);
		
		int aa = 0;
		aa++;
	}
}
