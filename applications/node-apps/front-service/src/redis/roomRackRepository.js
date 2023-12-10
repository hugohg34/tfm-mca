import redisClient from './redisClient.js';

const save = async (roomRack) => {
  const roomKey = `room:${roomRack.establishmentId}:${roomRack.id}`;
  await redisClient.hset(roomKey, '$', JSON.stringify(roomRack));

  const establishmentRoomsKey = `establishment:${roomRack.establishmentId}:rooms`;
  await redisClient.sadd(establishmentRoomsKey, roomRack.id);

  const roomKeyEstablishment = `room:${roomRack.id}`;
  await redisClient.hset(roomKeyEstablishment, 'id', roomRack.establishmentId);

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

const getById = async (roomId) => {
  const roomKeyEstablishment = `room:${roomId}`;
  const establishmentId = await redisClient.hgetall(roomKeyEstablishment);
  const roomKey = `room:${establishmentId.id}:${roomId}`;
  const roomRack = await redisClient.hgetall(roomKey);
  if(!roomRack) return null;
  return JSON.parse(roomRack.$);
}

export default { save, getByEstablishment, getById }