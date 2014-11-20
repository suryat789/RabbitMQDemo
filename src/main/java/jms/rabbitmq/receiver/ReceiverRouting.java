package jms.rabbitmq.receiver;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiverRouting {

	private static final String EXCHANGE_NAME = "DisneyExchange";
	private static final String ROUTING_KEY = "DisneyRoutingKey1";
	public static void main(String[] argv) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("10.74.230.148");
		factory.setUsername("disney");
		factory.setPassword("disney");

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
		
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, "");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);

		QueueingConsumer.Delivery delivery = null;
		String message = null;

		while (true) {
			delivery = consumer.nextDelivery();
			message = new String(delivery.getBody());
			System.out.println("RoutingKey: " + delivery.getEnvelope().getRoutingKey());
			System.out.println("Received '" + message + "'");   
		}
	}
}