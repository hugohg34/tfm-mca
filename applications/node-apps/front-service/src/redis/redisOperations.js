import redisClient from './redisClient.js';

export const saveOrUpdateRoomRack = async (roomDetails) => {
  const roomKey = `room-${roomDetails.id}`;
  const roomData = {};

  for (const [key, value] of Object.entries(roomDetails)) {
    roomData[key] = JSON.stringify(value);
  }

  await redisClient.hSet(roomKey, '$', JSON.stringify(roomDetails));

  const establishmentRoomsKey = `establishment:${roomDetails.establishment_id}:rooms`;
  await redisClient.sAdd(establishmentRoomsKey, roomKey);

};

export const deleteRoomRack = async (roomRack) => {
  try {
    const roomId = roomRack.id;
    const roomKey = `room:${roomId}`;
    const establishmentId = roomRack.establishment_id;

    if (establishmentId == '') {
      const roomData = await redisClient.hGetAll(roomKey);
      establishmentId = roomData.establishmentId;
    }

    if (establishmentId) {
      const establishmentRoomsKey = `establishment:${establishmentId}:rooms`;
      await redisClient.sRem(establishmentRoomsKey, roomKey);
    } else {
      console.log(`No se encontró el establishmentId para la habitación: ${roomId}`);
    }

    await redisClient.del(roomKey);
    console.log(`Room delete: ${roomId}`);

  } catch (error) {
    console.error('Error al eliminar la habitación:', error);
  }
}
