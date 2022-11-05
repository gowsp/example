#!/bin/sh
set -e

ME=$(basename $0)

entrypoint_log() {
    if [ -z "${NGINX_ENTRYPOINT_QUIET_LOGS:-}" ]; then
        echo "$@"
    fi
}
generate(){
  local ssl_dir="/etc/nginx/ssl"
  if [ -e $ssl_dir ]; then
      entrypoint_log "$ME: ssl dir exists, skipp generate"
      exit 0
  fi
  mkdir $ssl_dir
  local domain_name="${DOMAIN_NAME:-localhost}"
  entrypoint_log "$ME: start generate $domain_name cert"
  openssl req -x509 -newkey rsa:4096 -keyout "$ssl_dir/key.pem" -out "$ssl_dir/cert.pem" -sha256 -days 3650 \
    -nodes -subj "/CN=$domain_name"
  openssl dhparam -out "$ssl_dir/dhparam.pem" 2048
}
generate
exit 0
