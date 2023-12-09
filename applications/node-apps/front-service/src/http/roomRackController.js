import roomRackService  from '../services/roomRackService.js';

export const getRoomsRackByEstablishment = async (req, res) => {
  try {
    const establishmentId = req.params.establishmentId;
    const rooms = await roomRackService.getByEstablishment(establishmentId);
    res.json(rooms);
  } catch (error) {
    res.status(500).send(error.message);
  }
};
