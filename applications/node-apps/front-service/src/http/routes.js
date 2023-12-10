import express from "express";
import proxyController from "./roomRackProxyController.js";
import roomRackController from "./roomRackController.js";

const router = express.Router();

router.get('/establishments/:establishmentId/rooms', roomRackController.getByEstablishment);
router.get('/api/v1/*', proxyController.handelGet);
router.post('/*', proxyController.handlePost);
router.put('/*', proxyController.handelPut);

export default router;
