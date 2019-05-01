package cv;
public class TestMain {

	public static void main(String[] args) {
		ObjectReader test = new ObjectReader("C:\\Users\\Jakub Tomczak\\Desktop\\New folder (2)\\one.jpg");
		ObjectReader test1 = new ObjectReader("C:\\Users\\Jakub Tomczak\\Desktop\\New folder (2)\\two.jpg");
		ObjectReader test2 = new ObjectReader("C:\\Users\\Jakub Tomczak\\Desktop\\New folder (2)\\three.jpg");
		Thread th = new Thread(test);
		Thread th1 = new Thread(test1);
		Thread th2 = new Thread(test2);
		th.start();
		th1.start();
		th2.start();

	}

}
