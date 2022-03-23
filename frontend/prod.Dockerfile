FROM node:12-alpine as build
USER node
RUN mkdir /home/node/code/
WORKDIR /home/node/code/
COPY --chown=node:node package.json package-lock.json ./
RUN npm ci
COPY --chown=node:node . .
RUN npm run build

FROM nginx:1.17
COPY --from=build /home/node/code/build /usr/share/nginx/html