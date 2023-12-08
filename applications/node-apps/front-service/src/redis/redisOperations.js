import redisClient from './redisClient.js';

export const saveOrUpdateRoomRack = async (roomDetails) => {
  const roomKey = `room:${roomDetails.id}`;

  // Convierte y guarda cada propiedad del objeto roomDetails
  const entries = Object.entries(roomDetails).reduce((acc, [key, value]) => {
    acc.push(key, JSON.stringify(value)); // Convierte el valor a string
    return acc;
  }, []);

  console.log(entries);

  await redisClient.hSet(roomKey, ...entries);
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



// Uso correcto de hSet con múltiples argumentos
/*
RoomEventHandler before: {
  id: '9af1494e-a151-4a28-a556-3bbff58f3069',
  date_created: '1970-01-01T00:00:00Z',
  incident_active: false,
  is_clean: false,
  is_occupied: false,
  is_supervised: false,
  last_updated: '1970-01-01T00:00:00Z',
  name: null,
  room_number: 0,
  establishment_id: '',
  room_type_id: '

RoomEventHandler after: {
  id: '2381bc44-e1bd-411b-8ee0-e50cc6626883',
  date_created: '2023-12-07T20:23:39.218552Z',
  incident_active: true,
  is_clean: false,
  is_occupied: false,
  is_supervised: true,
  last_updated: '2023-12-07T20:23:39.218552Z',
  name: 'Room 1m',
  room_number: 1,
  establishment_id: '00000000-0000-0000-0000-000000000001',
  room_type_id: '112e7ca5-b70e-4bc1-a498-8ada8644dd22'
}
*/