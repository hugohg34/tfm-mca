FROM alpine:3.18.5
RUN apk --no-cache add curl \
    && addgroup -S appgroup && adduser -S appuser -G appgroup
COPY debezium-conf-post.sh /app/init-script.sh
RUN chmod +x /app/init-script.sh
USER appuser
WORKDIR /app
CMD ["./init-script.sh"]
