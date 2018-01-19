import com.henri.code.Register;
import org.junit.jupiter.api.Test;

public class RegisterTest {

    @Test
    public void test(){
        Register register = new Register();
        register.putBillCount(20, 1);
        register.putBillCount(10, 0);
        register.putBillCount(5, 3);
        register.putBillCount(2, 4);
        register.putBillCount(1, 0);

        System.out.println(register.show());

        String result = register.getChange(11);

        System.out.println(result);

        System.out.println(register.show());

    }

}
