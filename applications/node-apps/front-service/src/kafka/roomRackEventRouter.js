import roomRackService from '../services/roomRackService.js';

const handleRoomEvent = (event) => {
  const { op, before, after } = event.payload;

  switch (op) {
    case 'c': // Crear
    roomRackService.save(after);
      break;
    case 'u': // Actualizar
    roomRackService.update(after);
      break;
    case 'd': // Eliminar
    roomRackService.remove(before);
    default:
      console.log('Operación no reconocida:', op);
  }
};

const handerReservationDetailEvent = (event) => {
  const { op, before, after } = event.payload;
  switch (op) {
    case 'c': // Crear
    roomRackService.saveReservationDetail(after);
      break;
    case 'u': // Actualizar
    roomRackService.saveReservationDetail(after);
      break;
    case 'd': // Eliminar
    roomRackService.remove(before);
    default:
      console.log('Operación no reconocida:', op);
  }
}

export default {handleRoomEvent, handerReservationDetailEvent};
