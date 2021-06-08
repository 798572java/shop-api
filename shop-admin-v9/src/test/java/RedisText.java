import com.fh.shop.util.RedisUtil;
import org.junit.Test;

public class RedisText {


    @Test
    public  void test1(){
        RedisUtil.set("userName","张三");
        String userName = RedisUtil.get("userName");
        System.out.println(userName);

    }


}
