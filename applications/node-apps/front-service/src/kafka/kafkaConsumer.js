import kafka from './kafkaClient.js';
import roomRackRouter from './roomRackEventRouter.js';

const topicRoom = 'postgres-dbserver1.public.room';
const topicReservDetail = 'postgres-dbserver1.public.room_reservation_detail';
const consumer = kafka.consumer({ groupId: 'front-grupo' });

export const TOPICS = [topicRoom, topicReservDetail];

export const consume = async (topics) => {
  await consumer.connect();
  await Promise.all(topics.map(topic => consumer.subscribe({ topic, fromBeginning: true })));

  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      if (message.value === null) {
        return; // no process tombstone
      }
      const event = JSON.parse(message.value.toString());
      switch (topic) {
        case topicRoom:
          roomRackRouter.handleRoomEvent(event);
          break;
        case topicReservDetail:
          roomRackRouter.handerReservationDetailEvent(event);
          break;
        default:
          console.error('Tópico no reconocido:', topic);
      }
    },
    autoCommitThreshold: 50,
  });
};

