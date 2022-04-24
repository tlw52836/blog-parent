import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tlw.blog.BlogApp;
import com.tlw.blog.mapper.SysUserMapper;
import com.tlw.blog.mapper.pojo.SysUser;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = BlogApp.class)
public class MyTest {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void TestMD5() {
        System.out.println(DigestUtils.md5Hex("admin" + "tlw!@#"));
    }

    @Test
    public void TestMapper() {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, "www");

        System.out.println("=======" + sysUserMapper.selectList(queryWrapper));
    }


}
