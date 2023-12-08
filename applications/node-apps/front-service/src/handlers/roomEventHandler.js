import { saveOrUpdateRoomRack, deleteRoomRack } from '../redis/redisOperations.js';

const handleRoomEvent = (event) => {
  const { op, before, after } = event.payload;
  console.log('RoomEventHandler Operación:', op);
  console.log('RoomEventHandler before:', before);
  console.log('RoomEventHandler after:', after);

  switch (op) {
    case 'c': // Crear
      saveOrUpdateRoomRack(after);
      break;
    case 'u': // Actualizar
      saveOrUpdateRoomRack(after);
      break;
    case 'd': // Eliminar
      deleteRoomRack(before);
    default:
      console.log('Operación no reconocida:', op);
  }
};

export default handleRoomEvent;
