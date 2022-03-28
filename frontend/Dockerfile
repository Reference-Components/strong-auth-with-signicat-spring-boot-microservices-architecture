FROM node:12-alpine
USER node
RUN mkdir /home/node/code/
WORKDIR /home/node/code/
COPY --chown=node:node package.json package-lock.json ./
RUN npm ci
COPY --chown=node:node . .
ARG REACT_APP_GATEWAY_URI
ENV REACT_APP_GATEWAY_URI=$REACT_APP_GATEWAY_URI
CMD ["npm", "start"]