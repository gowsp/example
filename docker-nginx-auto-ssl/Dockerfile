FROM nginx:1.23.2-alpine
ENV DOMAIN_NAME=localhost HTTP_PORT=80 HTTPS_PORT=443
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories  \
    && apk add --no-cache openssl
COPY 40-auto-generate-certificates.sh /docker-entrypoint.d
COPY nginx /etc/nginx
