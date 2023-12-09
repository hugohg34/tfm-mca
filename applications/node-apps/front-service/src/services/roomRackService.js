import rRepo from '../redis/roomRackRepository.js';

const getByEstablishment = async (establishmentId) => {
    return rRepo.getByEstablishment(establishmentId);
}

const save = async (roomDetails) => {
    const roomDetWithOutUnderscore = convertKeysToCamelCase(roomDetails);
    rRepo.save(roomDetWithOutUnderscore);
}

const remove = async (roomDetails) => {
    console.log('Not implemented yet');
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

export default { getByEstablishment, save, remove }