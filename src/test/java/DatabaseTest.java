import com.henri.code.Database;
import com.henri.code.Register;
import org.junit.jupiter.api.Test;

public class DatabaseTest {

    @Test
    public void test(){
        Register db = new Register();
        db.putBillCount(20, 1);
        db.putBillCount(10, 1);
        db.putBillCount(5, 1);
        db.putBillCount(2, 1);
        db.putBillCount(1, 1);

        db.putBillCount(20, 1);


       String result = db.show();

       System.out.println(result);


    }

}
