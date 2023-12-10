import rRepo from '../redis/roomRackRepository.js';

const getByEstablishment = async (establishmentId) => {
    return rRepo.getByEstablishment(establishmentId);
}

const save = async (roomDetails) => {
    const roomRes = convertKeysToCamelCase(roomDetails);
    roomRes.reservationHolder = '';
    rRepo.save(roomRes);
}

const update = async (roomDetails) => {
    const roomRes = convertKeysToCamelCase(roomDetails);
    const room = await rRepo.getById(roomRes.id);
    roomRes.reservationHolder = room.reservationHolder;
    rRepo.save(roomRes);
}

const remove = async (roomDetails) => {
    console.error('Not implemented yet');
}

const saveReservationDetail = async (reservationDetail) => {
    const resDetail = convertKeysToCamelCase(reservationDetail);
    const roomId = resDetail.roomId;
    const room = await rRepo.getById(roomId);
    room.reservationHolder = resDetail.guestName;
    rRepo.save(room);
}

function toCamelCase(str) {
    const withoutPrefix = str.startsWith('is_') ? str.substring(3) : str;
    return withoutPrefix.replace(/_([a-z])/g, (g) => g[1].toUpperCase());
}

function convertKeysToCamelCase(obj) {
    return Object.keys(obj).reduce((acc, key) => {
        const camelCaseKey = toCamelCase(key);
        acc[camelCaseKey] = obj[key];
        return acc;
    }, {});
}

export default { getByEstablishment, save, update, remove, saveReservationDetail }