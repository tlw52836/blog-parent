import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tlw.blog.BlogApp;
import com.tlw.blog.dao.SysUserMapper;
import com.tlw.blog.dao.pojo.SysUser;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest(classes = BlogApp.class)
public class MyTest {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void TestMD5() {
        System.out.println(DigestUtils.md5Hex("admin" + "tlw!@#"));
    }

    @Test
    public void TestMapper() {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, "www");

        System.out.println("=======>" + sysUserMapper.selectList(queryWrapper));
    }


    @Test
    public void TestRedisTemplate() {
        System.out.println("=======>" + redisTemplate.opsForValue().get("tt"));
    }


}
