import { httpServer } from './express.js';

const port = 3000;

httpServer.listen(port, () => {
  console.log(`Servidor escuchando en http://localhost:${port}`);
});
