import { getRoomsRackByEstablishment } from '../services/roomRackService.js';

export const getRoomsRackByEstablishmentController = async (req, res) => {
  try {
    const establishmentId = req.params.establishmentId;
    const rooms = await getRoomsRackByEstablishment(establishmentId);
    res.json(rooms);
  } catch (error) {
    res.status(500).send(error.message);
  }
};
