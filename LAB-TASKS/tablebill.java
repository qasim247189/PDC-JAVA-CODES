public class tablebill{

        void Detail(){
        String tables = "Table 1";
        String waiter = "ali";
        float bill = (float) 2050.75;
        int cutomers= 4;
        boolean ispaid = true;
        char table_type = 'A';
        System.out.println("table  " + tables);
        System.out.println("waiter  " + waiter);
        System.out.println("bill  " + bill);
        System.out.println("customers  " + cutomers);
        System.out.println("ispaid  " + ispaid);
        System.out.println("table_type  " + table_type);
        }
        public static void main (String[] args){
            tablebill obj = new tablebill();
            obj.Detail();

        }
        
    }