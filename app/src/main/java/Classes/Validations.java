package Classes;

public class Validations {

    public static boolean isInt(String num){

        try {
            Integer.parseInt(num);
            return true;
        }catch (Exception e){
            return  false;
        }

    }

    public static boolean isDouble(String num){

        try {
            Double.parseDouble(num);
            return true;
        }catch (Exception e){
            return  false;
        }

    }

}
