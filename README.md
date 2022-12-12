## Del 1 DevOps-prinsipper

Beskriv med egne ord;

* Hva er utfordringene med dagens systemutviklingsprosess - og hvordan vil innføring av DevOps kunne være med på å løse
  disse? Hvilke DevOps prinsipper blir brutt?
Opptil 30% av prosjekter feiler grunnet dårlig etter ineffektiv kommunikasjon, systemer er også ikke produkter som man bygger kun en gang. 
I dag er alt digitalt basert på iterasjoner, fleste bygget på hverandre i form av versjoner. Markedet har et nødeløst krav på tilgjengelighet,
noe av det har røtter i versjons kontroll for elementer som operativ system/rammeverk oppgraderinger. 
DevOps sine kjerne premisser kan ikke sikre at alle problemer og risikoer under utvikling og drift av et prosjekt er løst men de reduserer en stor andel av dem.
Samarbeid i Devops er sett på i et større bilde enn bare fra team til team, kontinuerlig fly av informasjon er verdsatt mellom de større avdelingene i et
prosjekt som drift og utvikling. Tilknytningene teamene har mellom hverandre styrker hvor mye skin in the game de har. 
Automasjon og kontinuerlig integrering setter seg godt opp for kvalitet og versjons kontrollering. Automatisering et avgjørende komponent for CI/CD pipelinen
som kan redusere mye av de menneskelige feilene vi støter på under kvalitet og versjons kontrollering. 
 
  
* En vanlig respons på mange feil under release av ny funksjonalitet er å gjøre det mindre hyppig, og samtidig forsøke å legge på mer kontroll og QA. Hva er problemet med dette ut ifra et DevOps perspektiv, og hva kan være en bedre tilnærming?



* Teamet overleverer kode til en annen avdelng som har ansvar for drift - hva er utfordringen med dette ut ifra et DevOps perspektiv, og hvilke gevinster kan man få ved at team han ansvar for både drift- og utvikling? 

Devops går ut ifra kontinuerlig flyt mellom alle prosjekts stegene. Når man bryter vekk fra flyten som å segregere drift, 
mister man en del av kontrollen og flow'en som kommer med å ha ansvaret for begge partier. 
Mange problemer og mye kritisk informasjon oppstår under drift, dette gjør det viktig å skape så kort distanse som mulig mellom utvikling og drift. 
Hvis du sitter på både drift og utvikling skaper kan man et synkronisert miljø som gir kjappere respons tid på problemer, bedre tilnærming til continous feedback for kvalitets sikring og b.la hjelper til å opprettholde agile metoder.


* Å release kode ofte kan også by på utfordringer. Beskriv hvilke- og hvordan vi kan bruke DevOps prinsipper til å redusere
  eller fjerne risiko ved hyppige leveraner.
  
  
 

### Oppgave 3 

Branch protection og status sjekker - Beskriv hva sensor må gjøre for å konfigurere sin fork på en slik måte
at

* Ingen kan pushe kode direkte på main branch
* Kode kan merges til main branch ved å lage en Pull request med minst en godkjenning
* Kode kan merges til main bare når feature branchen som pull requesten er basert på, er verifisert av GitHub Actions.

Konfigurer branch til en beskyttet branch, dette gjør du i Settings/Branches i seksjonen «Branch Protection Rules».  
Velg «Add», 
deretter «Main» som branch, 
du blir gitt en liste med kriterier du kan huke av, her skal du velge:

o	Require a pull request before merging og Require approvals(sett antall approvals du vill ha). 
o	Require status check to pass before merging, velg så de actions du vill basere det på.
o	Do not allow bypassing the above settings


## Del 3 - Docker

### Oppgave 1

Beskriv hva du må gjøre for å få workflow til å fungere med din DockerHub konto? Hvorfor feiler workflowen? 

Workflowen feiler på grunn av mangel på Auth tilgang for login. I Github repoes har man tilgang til en feature som heter Secrets. 
Her kan man legge til Secrets spesifikt for Actions å bruke i tilfeller som autentiseringer. Github Secrets selv krypterer hemmeligheten,
men det er fremdeles foreslått og for de fleste tjenester pålagt å bruke en token i stedet for et direkte passord.  
For brukernavnet legger du inn en Secret med navnet DOCKER_HUB_USERNAME og ‘value’ satt til ditt brukernavn. På DockerHub siden genererer du en token,
denne legger du inn på samme vis som brukernavn men med navnet DOCKER_HUB_TOKEN og den genererte token som ‘value’.

### Oppave 3

* Beskriv med egne ord hva sensor må gjøre for å få sin fork til å laste opp container image til sitt eget ECR repo.

Siden sensor allerede har et repo så begynner man med å autentisere miljøet sitt mot AWS ECR, i dette tilfellet cloud9. 
Dette blir gjort i en terminal med kommandoen gitt under.

aws ecr get-login-password --region eu-west-1 | docker login --username AWS --password-stdin <aws-konto-nr>.dkr.ecr.<region>.amazonaws.com

Etter at du har autentisert deg kan du begynne å pushe et container image til ECR med disse kommando stegene.
o	docker build -t <ditt tagnavn>
o	docker tag <ditt tagnavn> <aws-konto-nr>.dkr.ecr.<region>.amazonaws.com/<ditt ECR repo navn>
o	docker push <aws-konto-nr>.dkr.ecr.<region>.amazonaws.com/<ditt repo navn>



## Del 5 - Terraform og CloudWatch Dashboards

i GitHub Actions. Workflowen kjørte bra første gang, men nå feiler den hver gang, og klager over at en bucket med samme navn allerede eksisterer.
Shopifly har tenkt på bruke denne bucketen til data-analyse.
```text
Error: creating Amazon S3 (Simple Storage) Bucket (analytics-jim): BucketAlreadyOwnedByYou: 
Your previous request to create the named bucket succeeded and you already own it.
```

### Oppgave 1 

Se på ```provider.tf filen```. 

* Forklar med egne ord. Hva er årsaken til dette problemet? Hvorfor forsøker Terraform å opprette en bucket, når den allerede eksisterer? 
Terraform default oppretter en ny bucket hvis den ikke vet at en eksisterer. Så når du ikke presist sier til terraform hvor en etablert bucket befinner seg,
lager den en ny med info vi har satt.
