package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        for(Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            logger.info("Constructors :: {}", constructor);
        }

        for(Field field : clazz.getDeclaredFields()){
            logger.info("Fields :: {}", field.getModifiers());
        }

        for(Method method : clazz.getDeclaredMethods()){
            logger.info("Methods :: {}", method);
        }
    }
    
    @Test
    public void newInstanceWithConstructorArgs() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        Constructor<User> constructor = (Constructor<User>) clazz.getDeclaredConstructors()[0];
        User user = constructor.newInstance("LichKing", "PW", "ChangYong", "EM");

        assertThat(user, is(notNullValue()));
        assertThat(user.getUserId(), is("LichKing"));
        assertThat(user.getPassword(), is("PW"));
        assertThat(user.getName(), is("ChangYong"));
        assertThat(user.getEmail(), is("EM"));
    }
    
    @Test
    public void privateFieldAccess() throws IllegalAccessException, InstantiationException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = clazz.newInstance();
        for(Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);
            if("name".equals(field.getName())){
                field.set(student, "hello");
            }

            if("age".equals(field.getName())){
                field.set(student, 20);
            }
        }

        assertThat(student.getAge(), is(20));
        assertThat(student.getName(), is("hello"));
    }
}
