package jms.rabbitmq.sender;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Sender {

	private static final String EXCHANGE_NAME = "TestExchange";

	public static void main(String[] argv) throws Exception {
		
		String message = null;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("10.74.230.148");
		factory.setUsername("disney");
		factory.setPassword("disney");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

		for(int i=1; i<=2000; i++){
			message = "A test message with MsgID - " + i;
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		}
		channel.close();
		connection.close();
	}

}