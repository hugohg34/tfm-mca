import roomRackService  from '../services/roomRackService.js';

const getByEstablishment = async (req, res) => {
  try {
    const establishmentId = req.params.establishmentId;
    const rooms = await roomRackService.getByEstablishment(establishmentId);
    res.json(rooms);
  } catch (error) {
    res.status(500).send(error.message);
    console.error('getRoomsRackByEstablishment', error);
  }
};

export default { getByEstablishment };
