import cn.edu.dao.AddrTimeDao;
import cn.edu.dao.DsDao;
import cn.edu.dao.StudentImageDao;
import cn.edu.dao.StudentInfoDao;
import cn.edu.entity.AddrTimeBean;
import cn.edu.entity.DsBean;
import cn.edu.entity.StudentImageBean;
import cn.edu.entity.StudentInfoBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author lbc
 * @description: TODO
 * @date 2019/3/3 18:58
 */
public class SpringTest {

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring/spring-dao.xml");
    }
}
