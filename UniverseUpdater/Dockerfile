FROM python:3
#FROM mongo:latest

WORKDIR /usr/src/universeupdater

#EXPOSE 27017
COPY source source/
COPY requirements.txt .
# install Python 3
#RUN apt-get update && apt-get install -y python3 python3-pip
#RUN apt-get -y install python3.7-dev
#RUN pip3 install pymongo
RUN pip install -r requirements.txt
RUN ls

CMD ["python", "-m", "source.updater.universeupdater"]

EXPOSE 8000