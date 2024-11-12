package cn.wnhyang.coolGuard.util;

/**
 * @author wnhyang
 * @date 2024/11/8
 **/
public class GeoHash {
    /**
     * geoHash 32位
     */
    private static final char[] BASE32 = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * 纬度范围
     */
    private static final double MIN_LAT = -90.0, MAX_LAT = 90.0;

    /**
     * 经度范围
     */
    private static final double MIN_LON = -180.0, MAX_LON = 180.0;

    /**
     * 将给定的纬度和经度编码为GeoHash字符串，指定精度。
     *
     * @param latitude  纬度[-90,90]
     * @param longitude 经度[-180,180]
     * @param precision 精度[1,12]
     * @return GeoHash字符串
     */
    public static String encode(double latitude, double longitude, int precision) {
        if (latitude < MIN_LAT || latitude > MAX_LAT || longitude < MIN_LON || longitude > MAX_LON) {
            throw new IllegalArgumentException("Latitude and longitude must be within valid ranges.");
        }
        if (precision <= 0 || precision > 12) {
            throw new IllegalArgumentException("precision must between 1 and 12");
        }

        long geoHashBits = 0;
        boolean isEven = true;
        double minLat = MIN_LAT, maxLat = MAX_LAT;
        double minLon = MIN_LON, maxLon = MAX_LON;
        int bitIndex = 0;
        // 每个字符代表5位二进制数
        int requiredBits = precision * 5;
        while (bitIndex < requiredBits) {
            double midValue;
            if (isEven) {
                midValue = (minLon + maxLon) / 2;
                if (longitude > midValue) {
                    geoHashBits |= (1L << (requiredBits - bitIndex - 1));
                    minLon = midValue;
                } else {
                    maxLon = midValue;
                }
            } else {
                midValue = (minLat + maxLat) / 2;
                if (latitude > midValue) {
                    geoHashBits |= (1L << (requiredBits - bitIndex - 1));
                    minLat = midValue;
                } else {
                    maxLat = midValue;
                }
            }
            isEven = !isEven;
            bitIndex++;
        }

        return bitsToBase32(geoHashBits, precision);
    }

    /**
     * 将给定的二进制位转换为GeoHash字符串。
     *
     * @param bits      二进制位
     * @param precision 精度
     * @return GeoHash字符串
     */
    private static String bitsToBase32(long bits, int precision) {
        char[] base32Chars = new char[precision];
        for (int i = 0; i < precision; i++) {
            // Extract 5 bits
            int index = (int) ((bits >>> (i * 5)) & 0x1F);
            base32Chars[precision - i - 1] = BASE32[index];
        }
        return new String(base32Chars);
    }

    /**
     * 将给定的经度和纬度转换为7位GeoHash字符串。
     *
     * @param latitude  纬度[-90,90]
     * @param longitude 经度[-180,180]
     * @return GeoHash字符串
     */
    public static String geoHash7(double latitude, double longitude) {
        return encode(latitude, longitude, 7);
    }

    /**
     * 将给定的经度和纬度转换为12位GeoHash字符串。
     *
     * @param lonAndLat 经纬度字符串，格式为"经度,纬度"
     * @return GeoHash字符串
     */
    public static String geoHash(String lonAndLat) {
        try {
            String[] split = lonAndLat.split(",");
            double lon = Double.parseDouble(split[0]);
            double lat = Double.parseDouble(split[1]);
            return encode(lat, lon, 12);
        } catch (Exception e) {
            return "#";
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // GeoHash 字符串长度
        int precision = 9;
        //30.2549529076, 120.1646161079
        System.out.println(encode(30.2549958229, 120.1647019386, precision));
        System.out.println(geoHash7(30.2549958229, 120.1647019386));
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }
}
