# inventory-system

Inventory-System is a simple full-stack application that accompanies an inventory-system n8n workflow. This workflow
allows to take pictures of items, have an AI recognize what's on them and store that information in the backend.

The frontend is built using ReactJS and the backend is build in Java Spring Boot.
A CI/CD pipeline is implemented locally to automatically build and deploy the application in a home-lab.

During development, frontend and backend are run seperately. The frontend is served using `npm run dev` and
runs through the `vite proxy` in order to access the backend at http://localhost:8080/ without needing absolute
request paths. 