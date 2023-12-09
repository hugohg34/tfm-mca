//TODO Esto realmente es un controlador? un router?
import roomRackService from '../services/roomRackService.js';

const handleRoomEvent = (event) => {
  const { op, before, after } = event.payload;

  switch (op) {
    case 'c': // Crear
    roomRackService.save(after);
      break;
    case 'u': // Actualizar
    roomRackService.save(after);
      break;
    case 'd': // Eliminar
    roomRackService.remove(before);
    default:
      console.log('Operación no reconocida:', op);
  }
};

export default handleRoomEvent;
