FROM ubuntu:lunar
MAINTAINER youngyicode@outlook.com
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone
WORKDIR /workspace
EXPOSE 8080
ADD ./build/native/nativeCompile/free-cloud-native-practice ./
RUN chmod +x free-cloud-native-practice
ENTRYPOINT ["./free-cloud-native-practice"]
