package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class Junit3TestRunner {
    private static final Logger logger = LoggerFactory.getLogger(Junit3TestRunner.class);

    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;

        for(Method method : clazz.getMethods()){
            if(method.getName().startsWith("test")){
                method.invoke(clazz.newInstance());
            }
            logger.info("method :: {}", method.getName());
        }
    }
}
