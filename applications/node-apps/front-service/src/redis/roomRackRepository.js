import redisClient from './redisClient.js';

const save = async (roomRack) => {

  const roomKey = `room:${roomRack.establishmentId}:${roomRack.id}`;
  await redisClient.hset(roomKey, '$', JSON.stringify(roomRack));

  const establishmentRoomsKey = `establishment:${roomRack.establishmentId}:rooms`;
  await redisClient.sadd(establishmentRoomsKey, roomRack.id);
}

const getByEstablishment = async (establishmentId) => {

  const establishmentRoomsKey = `establishment:${establishmentId}:rooms`;
  const roomIds = await redisClient.smembers(establishmentRoomsKey);

  const pipeline = redisClient.pipeline();
  for (const roomId of roomIds) {
    const roomKey = `room:${establishmentId}:${roomId}`;
    pipeline.hgetall(roomKey);
  }

  const rooms = [];
  const results = await pipeline.exec();
  for (const result of results) {
    const roomRack = result[1];
    rooms.push(JSON.parse(roomRack.$));
  }

  return rooms;
}

export default { save, getByEstablishment }