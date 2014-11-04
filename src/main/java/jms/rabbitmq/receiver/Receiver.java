package jms.rabbitmq.receiver;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Receiver {

	private static final String EXCHANGE_NAME = "TestExchange";

	public static void main(String[] argv) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("10.74.230.148");
		factory.setUsername("xxx");
		factory.setPassword("xxx");

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, "");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);

		QueueingConsumer.Delivery delivery = null;
		String message = null;

		while (true) {
			delivery = consumer.nextDelivery();
			message = new String(delivery.getBody());

			System.out.println("Received '" + message + "'");   
		}
	}
}