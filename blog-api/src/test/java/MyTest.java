import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

public class MyTest {

    @Test
    public void TestMD5() {
        System.out.println(DigestUtils.md5Hex("2695684aaa" + "tlw!@#"));
    }
}
