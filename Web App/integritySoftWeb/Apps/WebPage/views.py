from django.shortcuts import render
from django.core.cache import cache
import requests
import json

base_url="http://localhost:3000/api/web"

# Create your views here.
def loginPage(request):
    return render(request,'sessions/login_Register.html')

def login(request):
    info = {
        'email':request.POST.get('emailLog'),
        'pass' :request.POST.get('passLog')
    }
    r = requests.post(base_url+'/logear',data=info)
    user = json.loads(r.text)
    if r.status_code != 404:
        cache.set('sesion', True, 60 * 5)
        return render (request,'index.html',{'usuario': user, 'sesion':cache.get('sesion')})
    else:
        request.msg = user.body.msg
        return render (request,'sessions/login_Register.html')


def logOut(request):
    pass

def home(request):
    return render(request,'index.html',{'sesion':cache.get('sesion')})

def formulario(request):
    return render(request,'formulario.html')

def sugerencia(request):
    #enviar mensaje a la api
    info = {
        'nombre' : request.POST.get('nombre'),
        'email'  : request.POST.get('email'),
        'telefono':request.POST.get('telefono'),
        'mensaje': request.POST.get('mensaje')
    }
    r = requests.post(base_url+"/recibirFormulario",data=info)
    json_data = json.loads(r.text)

    return render(request,'index.html')

def perfil(request):
    return render(request,'sessions/perfil.html')