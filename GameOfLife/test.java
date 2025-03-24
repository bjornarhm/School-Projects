public class test {
    public static void main(String[] args) {
        Verden verden = new Verden(8, 12);
        System.out.println("0. generasjon:");
        verden.tegn();
        for (int i = 1; i <= 100; i++) {
            verden.oppdatering();
            System.out.println(i + ". generasjon:");
            verden.tegn();
            System.out.println("");
        }
    }
}