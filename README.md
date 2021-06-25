# Events

Aplicación desarrollado con **Spring Boot Framework** usando MySQL para la persistencia de los datos

Clonar el repositorio https://github.com/joe-watson-sbf/Events.git o descargarlo normal en el boton de descargar : ![image](https://user-images.githubusercontent.com/49710538/123399527-52f57600-d56a-11eb-8d9d-c59aa559f50d.png)

El servicio **API** es muy sencillo:

 1) Hay 5 instructores con datos de ejemplos que el servicio va retornar en un formato Json:
     
     ***URL : GET Method*** : http://localhost:8080/api/v1/instructor
     
 La lista de retorno:
 
         [
            {
                "id": 1,
                "firstName": "Jacob",
                "lastName": "Smith",
                "birthday": "08/02/1993",
                "events": []
            },
            {
                "id": 2,
                "firstName": "Brandon",
                "lastName": "Johnson",
                "birthday": "25/12/1990",
                "events": []
            },
            {
                "id": 3,
                "firstName": "Adrian",
                "lastName": "Robinson",
                "birthday": "11/05/1987",
                "events": []
            },
            {
                "id": 4,
                "firstName": "Amy",
                "lastName": "Scott",
                "birthday": "03/07/1989",
                "events": []
            },
            {
                "id": 5,
                "firstName": "Kim",
                "lastName": "Anderson",
                "birthday": "14/09/1992",
                "events": []
            }
        ]
        
 _El servicio no se permite ingresar instructores, solo funciona con esos datos de ejemplos_
 
 2) CRUD de eventos
     
     Crear eventos: 2 metodos son disponible para crear eventos
     
      1 - Un solo evento:
      
       URL: http://localhost:8080/api/v1/event
       Json formato: 
       
               {
                   "start": "2021-07-15T13:00:00.681+00:00",
                   "end": "2021-07-17T14:00:00.681+00:00",
                   "type": "exemple",
                   "description": "exemple!"
               }

       Respuesta de retorno: 
         - En caso que sea exitoso: 

               {
                   "response": "Event Created!"
               }
               
               
       - Al contrario:
        
              {
                  "response": "Se dice dondé está la falla..."
              }
              
              
       1 - Un lista de eventos:
        URL: http://localhost:8080/api/v1/events
        Json formato: 
         
                [
                    {
                        "start": "2021-06-24T13:00:00.681+00:00",
                        "end": "2021-06-28T14:00:00.681+00:00",
                        "type": "1 week off",
                        "description": "Pause!"
                    },
                    {
                        "start": "2021-06-28T13:00:00.681+00:00",
                        "end": "2021-06-30T14:00:00.681+00:00",
                        "type": "2 seminars at 1 week each",
                        "description": "seminars!"
                    },
                    {
                        "start": "2021-07-17T13:00:00.681+00:00",
                        "end": "2021-07-30T14:00:00.681+00:00",
                        "type": "1 project week",
                        "description": "project!"
                    }
                ]

       Respuesta de retorno: 
       - En caso que sea exitoso: 
                    
                    {
                        "response": "Event Created!"
                    }
                    
       - Al contrario:
        
                    {
                        "response": "Se dice dondé está la falla..."
                    }


