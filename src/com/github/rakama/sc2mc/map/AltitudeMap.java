package com.github.rakama.sc2mc.map;

public class AltitudeMap
{
    public static int width = 128;
    public static int height = 128;
    
    int[] terrain;
    int[] water;
    
    public AltitudeMap(byte[] data)
    {
        terrain = new int[128 * 128];
        water = new int[128 * 128];
        
        for(int x=0; x<width; x++)
        {
            for(int y=0; y<height; y++)
            {
                int value = getUInt16(data, x, y);
                int index = toIndex(x, y);
                terrain[index] = value & 0xF;
                water[index] =  (value >> 5) & 0xF;
            }
        }
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public int getTerrainAltitude(int x, int y)
    {
        checkBounds(x, y);
        return terrain[toIndex(x, y)];
    }
    
    public int getWaterAltitude(int x, int y)
    {
        checkBounds(x, y);
        return water[toIndex(x, y)];
    }
    
    protected static int getUInt16(byte[] data, int x, int y)
    {
        int index = ((127 - x) + (y << 7)) << 1;
        int byte1 = 0xFF & data[index];
        int byte2 = 0xFF & data[index + 1];
        return (byte1 << 8) | byte2;
    }
    
    protected static int toIndex(int x, int y)
    {
        return x + (y << 7);
    }

    protected static void checkBounds(int x, int y)
    {
        if(!inBounds(x, y))
            throw new IndexOutOfBoundsException("(" + x + ", " + y + ")");
    }

    protected static boolean inBounds(int x, int y)
    {
        return x == (x & 0x7F) && y == (y & 0x7F);
    }
}