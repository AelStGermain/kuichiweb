/**
 * KUICHIWEB - DEMO APP ENGINE
 * Demo interactiva para GitHub Pages con gestión de estado en localStorage.
 */

// --- DATOS SEMILLA (Basados en DataSeeder.java) ---
const INITIAL_CLINICS = [
  {
    id: 1,
    name: "Hospital Clínico Veterinario U. de Chile",
    address: "Av. Cristóbal Colón 3018, Providencia",
    commune: "Providencia",
    phone: "+56229785555",
    email: "contacto@hospitalveterinario.cl",
    website: "https://www.hospitalveterinario.cl",
    openingHours: "24 Horas",
    emergency247: true,
    description: "Atención de alta complejidad, cirugía y especialidades. Referente nacional con laboratorios de última generación.",
    imageUrl: "https://images.unsplash.com/photo-1530041539828-114de669390e?q=80&w=1000&auto=format&fit=crop",
    reviews: [
      { id: 101, author: "María López", rating: 5, comment: "Excelente atención, muy profesionales. Mi perro quedó perfectamente después de la cirugía." },
      { id: 102, author: "Carlos Pérez", rating: 4, comment: "Buen servicio, aunque la sala de espera estaba concurrida. Los precios son razonables." }
    ]
  },
  {
    id: 2,
    name: "Clínica Veterinaria SOS",
    address: "Av. Pajaritos 3195, Maipú",
    commune: "Maipú",
    phone: "+56987654321",
    email: "contacto@vetsos.cl",
    website: "https://www.vetsos.cl",
    openingHours: "Lun-Sab 09:00 - 20:00",
    emergency247: false,
    description: "Medicina preventiva, vacunatorio, ecografías y peluquería canina especializada.",
    imageUrl: "https://images.unsplash.com/photo-1628009368231-7bb7cfcb0def?q=80&w=1000&auto=format&fit=crop",
    reviews: [
      { id: 103, author: "Ana Silva", rating: 5, comment: "Mi gato estaba decaído y lo atendieron con mucho cariño. ¡Muy recomendados en Maipú!" }
    ]
  },
  {
    id: 3,
    name: "Clínica Alemana Veterinaria",
    address: "Av. Vitacura 9999, Vitacura",
    commune: "Vitacura",
    phone: "+56222223333",
    email: "info@alemanavet.cl",
    website: "https://www.alemanavet.cl",
    openingHours: "24 Horas",
    emergency247: true,
    description: "Tecnología de punta para el diagnóstico de tu mascota, hospitalización UCI y resonancia magnética.",
    imageUrl: "https://images.unsplash.com/photo-1666214280557-f1b5022eb634?q=80&w=1000&auto=format&fit=crop",
    reviews: [
      { id: 104, author: "Admin Kuichi", rating: 4, comment: "Tecnología de nivel internacional e instalaciones higiénicas." },
      { id: 105, author: "María López", rating: 3, comment: "Atención impecable pero precios elevados. El servicio es de calidad." }
    ]
  }
];

const INITIAL_OFFERS = [
  { id: 1, clinicId: 1, clinicName: "Hospital Clínico U. de Chile", title: "30% Dscto. Ecografías", description: "Válido para pacientes derivados de consulta interna solo por este mes.", discount: 30, code: "ECO30KUICHI" },
  { id: 2, clinicId: 2, clinicName: "Clínica Veterinaria SOS", title: "Chip Gratis con Plan Anual", description: "Al contratar cualquier plan de vacunación preventiva anual.", discount: 100, code: "CHIPFREE" },
  { id: 3, clinicId: 3, clinicName: "Clínica Alemana Veterinaria", title: "2x1 en Baño y Corte", description: "Promoción válida para perros de raza pequeña de Lunes a Miércoles.", discount: 50, code: "SPA2X1" }
];

const INITIAL_PETS = [
  {
    id: 1,
    name: "Max",
    species: "Perro",
    breed: "Golden Retriever",
    age: 3,
    vaccinated: true,
    chipNumber: "CHIP-9821039",
    notes: "Alergia leve al pollo. Le gusta nadar.",
    imageUrl: "https://images.unsplash.com/photo-1552053831-71594a27632d?q=80&w=800&auto=format&fit=crop"
  },
  {
    id: 2,
    name: "Luna",
    species: "Gato",
    breed: "Siamés",
    age: 2,
    vaccinated: true,
    chipNumber: "CHIP-4410921",
    notes: "Esquema de vacunas completo. Muy juguetona.",
    imageUrl: "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?q=80&w=800&auto=format&fit=crop"
  },
  {
    id: 3,
    name: "Toby",
    species: "Perro",
    breed: "Beagle",
    age: 4,
    vaccinated: true,
    chipNumber: "CHIP-1029384",
    notes: "Chequeo cardíaco anual OK.",
    imageUrl: "https://images.unsplash.com/photo-1537151608828-ea2b11777ee8?q=80&w=800&auto=format&fit=crop"
  }
];

// --- ESTADO DE LA APLICACIÓN ---
class KuichiState {
  constructor() {
    this.role = localStorage.getItem('kuichi_role') || 'USER'; // GUEST, USER, ADMIN
    this.clinics = JSON.parse(localStorage.getItem('kuichi_clinics')) || INITIAL_CLINICS;
    this.offers = JSON.parse(localStorage.getItem('kuichi_offers')) || INITIAL_OFFERS;
    this.pets = JSON.parse(localStorage.getItem('kuichi_pets')) || INITIAL_PETS;
  }

  saveClinics() { localStorage.setItem('kuichi_clinics', JSON.stringify(this.clinics)); }
  saveOffers() { localStorage.setItem('kuichi_offers', JSON.stringify(this.offers)); }
  savePets() { localStorage.setItem('kuichi_pets', JSON.stringify(this.pets)); }
  setRole(newRole) {
    this.role = newRole;
    localStorage.setItem('kuichi_role', newRole);
  }
}

const state = new KuichiState();

// --- INICIALIZACIÓN ---
document.addEventListener('DOMContentLoaded', () => {
  setupNavigation();
  setupRoleSwitcher();
  setupHeroSearch();
  setupHeroSlider();
  renderHome();
  renderClinics();
  renderOffers();
  renderPets();
  populateCommunesFilter();
});

// --- RENDERIZADO DE VISTAS Y NAVEGACIÓN ---
function setupNavigation() {
  const links = document.querySelectorAll('.nav-link');
  links.forEach(link => {
    link.addEventListener('click', (e) => {
      e.preventDefault();
      const targetTab = link.getAttribute('data-tab');
      switchTab(targetTab);
    });
  });
}

function switchTab(tabId) {
  document.querySelectorAll('.tab-page').forEach(page => page.style.display = 'none');
  document.querySelectorAll('.nav-link').forEach(link => link.classList.remove('active'));

  const activePage = document.getElementById(`tab-${tabId}`);
  const activeLink = document.querySelector(`.nav-link[data-tab="${tabId}"]`);

  if (activePage) activePage.style.display = 'block';
  if (activeLink) activeLink.classList.add('active');

  window.scrollTo({ top: 0, behavior: 'smooth' });
}

function setupRoleSwitcher() {
  const roleSelect = document.getElementById('roleSelect');
  if (roleSelect) {
    roleSelect.value = state.role;
    roleSelect.addEventListener('change', (e) => {
      state.setRole(e.target.value);
      updateRoleUI();
      showToast(`Modo cambiado a: ${e.target.value}`, 'success');
    });
  }
  updateRoleUI();
}

function updateRoleUI() {
  const roleLabel = document.getElementById('currentRoleLabel');
  const adminTab = document.getElementById('nav-admin-tab');
  const petsTab = document.getElementById('nav-pets-tab');
  const userGreeting = document.getElementById('userGreeting');

  if (roleLabel) roleLabel.textContent = state.role;
  if (adminTab) adminTab.style.display = (state.role === 'ADMIN') ? 'inline-block' : 'none';
  if (petsTab) petsTab.style.display = (state.role === 'GUEST') ? 'none' : 'inline-block';
  
  if (userGreeting) {
    userGreeting.textContent = state.role === 'ADMIN' ? 'Modo Administrador' :
                               state.role === 'USER' ? '¡Hola, Usuario PetLover! 🐾' : 'Bienvenido a KuichiWeb';
  }
}

// --- RENDERIZADO DE COMPONENTES ---

// Calc rating promedio
function calcAverageRating(reviews) {
  if (!reviews || reviews.length === 0) return 5.0;
  const sum = reviews.reduce((acc, r) => acc + r.rating, 0);
  return (sum / reviews.length).toFixed(1);
}

// 1. INICIO (Home)
function renderHome() {
  // Render de ofertas destacadas
  const homeOffersContainer = document.getElementById('home-offers-grid');
  if (homeOffersContainer) {
    homeOffersContainer.innerHTML = state.offers.slice(0, 3).map(offer => `
      <div class="card">
        <div class="card-body">
          <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:12px;">
            <span style="background:var(--secondary-gradient); color:white; padding:4px 12px; border-radius:50px; font-weight:800; font-size:0.85rem;">-${offer.discount}% DESCUENTO</span>
            <small style="color:var(--text-muted); font-weight:700;"><i class="fas fa-tag"></i> Oferta</small>
          </div>
          <h3 class="card-title">${escapeHtml(offer.title)}</h3>
          <p style="color:var(--text-muted); font-size:0.95rem; margin-bottom:16px;">${escapeHtml(offer.description)}</p>
          <div style="font-weight:700; color:var(--primary); font-size:0.9rem; margin-bottom:16px;">
            <i class="fas fa-hospital"></i> ${escapeHtml(offer.clinicName)}
          </div>
          <button onclick="claimOffer('${offer.code}')" class="btn btn-primary" style="width:100%; justify-content:center;">
            <i class="fas fa-ticket-alt"></i> Obtener Cupón (${offer.code})
          </button>
        </div>
      </div>
    `).join('');
  }

  // Render clínicas destacadas
  const homeClinicsContainer = document.getElementById('home-clinics-grid');
  if (homeClinicsContainer) {
    homeClinicsContainer.innerHTML = state.clinics.slice(0, 3).map(clinic => renderClinicCardHTML(clinic)).join('');
  }
}

// 2. CLÍNICAS (Directory)
function renderClinicCardHTML(clinic) {
  const avgRating = calcAverageRating(clinic.reviews);
  const reviewsCount = clinic.reviews ? clinic.reviews.length : 0;

  return `
    <div class="card">
      <div class="card-img-wrapper">
        <img src="${clinic.imageUrl}" alt="${escapeHtml(clinic.name)}" onerror="this.src='https://images.unsplash.com/photo-1530041539828-114de669390e?q=80'">
        ${clinic.emergency247 ? '<span class="badge-247"><i class="fas fa-ambulance"></i> 24/7 Urgencias</span>' : ''}
        <span class="badge-commune"><i class="fas fa-map-marker-alt"></i> ${escapeHtml(clinic.commune || 'Santiago')}</span>
      </div>
      <div class="card-body">
        <h3 class="card-title">${escapeHtml(clinic.name)}</h3>
        <div class="card-info"><i class="fas fa-map-pin" style="color:var(--primary);"></i> ${escapeHtml(clinic.address)}</div>
        <div class="card-info"><i class="fas fa-clock" style="color:var(--text-muted);"></i> ${escapeHtml(clinic.openingHours)}</div>
        
        <div class="rating-stars">
          <span>${avgRating}</span>
          <div>${getStarIconsHTML(avgRating)}</div>
          <small style="color:var(--text-muted); font-weight:600;">(${reviewsCount} opiniones)</small>
        </div>
        
        <p style="font-size:0.9rem; color:var(--text-muted); margin-bottom:15px; display:-webkit-box; -webkit-line-clamp:2; -webkit-box-orient:vertical; overflow:hidden;">
          ${escapeHtml(clinic.description)}
        </p>

        <div class="card-footer">
          <a href="tel:${clinic.phone}" class="btn btn-secondary" style="padding:8px 14px; font-size:0.85rem;" title="Llamar">
            <i class="fas fa-phone-alt" style="color:var(--success);"></i> ${escapeHtml(clinic.phone)}
          </a>
          <button onclick="openClinicModal(${clinic.id})" class="btn btn-primary" style="padding:8px 16px; font-size:0.85rem;">
            Ver Detalles
          </button>
        </div>
      </div>
    </div>
  `;
}

function renderClinics() {
  const container = document.getElementById('clinics-grid');
  if (!container) return;

  const searchVal = (document.getElementById('clinicSearchInput')?.value || '').toLowerCase();
  const communeVal = document.getElementById('clinicCommuneFilter')?.value || 'ALL';
  const emergencyVal = document.getElementById('clinicEmergencyFilter')?.checked || false;

  const filtered = state.clinics.filter(c => {
    const matchesSearch = c.name.toLowerCase().includes(searchVal) || 
                          c.address.toLowerCase().includes(searchVal) ||
                          c.description.toLowerCase().includes(searchVal);
    const matchesCommune = communeVal === 'ALL' || (c.commune && c.commune === communeVal);
    const matchesEmergency = !emergencyVal || c.emergency247;
    return matchesSearch && matchesCommune && matchesEmergency;
  });

  if (filtered.length === 0) {
    container.innerHTML = `
      <div style="grid-column:1/-1; text-align:center; padding:60px 20px; background:white; border-radius:var(--radius-lg);">
        <i class="fas fa-search-location fa-3x" style="color:#cbd5e1; margin-bottom:15px;"></i>
        <h3>No se encontraron clínicas</h3>
        <p style="color:var(--text-muted);">Prueba ajustando los filtros de búsqueda o comuna.</p>
      </div>
    `;
    return;
  }

  container.innerHTML = filtered.map(c => renderClinicCardHTML(c)).join('');
}

function populateCommunesFilter() {
  const communeSelect = document.getElementById('clinicCommuneFilter');
  if (!communeSelect) return;

  const communes = Array.from(new Set(state.clinics.map(c => c.commune || 'Santiago'))).sort();
  communeSelect.innerHTML = '<option value="ALL">Todas las Comunas</option>' + 
    communes.map(comm => `<option value="${comm}">${comm}</option>`).join('');
}

// 3. MODAL DETALLE DE CLÍNICA Y RESEÑAS
function openClinicModal(clinicId) {
  const clinic = state.clinics.find(c => c.id === clinicId);
  if (!clinic) return;

  const modal = document.getElementById('clinicModal');
  const body = document.getElementById('clinicModalBody');

  const avgRating = calcAverageRating(clinic.reviews);

  body.innerHTML = `
    <div style="position:relative; height:240px; border-radius:var(--radius-md); overflow:hidden; margin-bottom:20px;">
      <img src="${clinic.imageUrl}" style="width:100%; height:100%; object-fit:cover;">
      <div style="position:absolute; bottom:15px; left:20px; color:white; text-shadow:0 2px 10px rgba(0,0,0,0.7);">
        <h2 style="color:white; margin:0; font-size:1.8rem;">${escapeHtml(clinic.name)}</h2>
        <span style="background:rgba(255,255,255,0.25); backdrop-filter:blur(5px); padding:4px 12px; border-radius:50px; font-size:0.85rem; font-weight:700;">
          <i class="fas fa-map-marker-alt"></i> ${escapeHtml(clinic.address)}
        </span>
      </div>
    </div>

    <div style="display:flex; gap:15px; flex-wrap:wrap; margin-bottom:20px; background:#f8fafc; padding:16px; border-radius:var(--radius-md);">
      <div><strong>Horario:</strong> ${escapeHtml(clinic.openingHours)}</div>
      <div><strong>Teléfono:</strong> <a href="tel:${clinic.phone}" style="color:var(--primary); font-weight:700;">${escapeHtml(clinic.phone)}</a></div>
      <div><strong>Atención 24/7:</strong> ${clinic.emergency247 ? '✅ Sí' : '❌ No'}</div>
      ${clinic.website ? `<div><strong>Sitio Web:</strong> <a href="${clinic.website}" target="_blank" style="color:var(--primary);">${escapeHtml(clinic.website)}</a></div>` : ''}
    </div>

    <p style="margin-bottom:24px; color:var(--text-main); font-size:1rem; line-height:1.7;">
      ${escapeHtml(clinic.description)}
    </p>

    <hr style="border:0; border-top:1px solid var(--border-color); margin:24px 0;">

    <h3 style="margin-bottom:16px; display:flex; justify-content:space-between; align-items:center;">
      <span><i class="fas fa-comments" style="color:var(--primary);"></i> Opiniones y Reseñas</span>
      <span style="font-size:1.1rem; color:#ffc107;">★ ${avgRating} / 5</span>
    </h3>

    <div style="display:flex; flex-direction:column; gap:12px; margin-bottom:24px;">
      ${(clinic.reviews && clinic.reviews.length > 0) ? clinic.reviews.map(r => `
        <div style="background:#f8fafc; border-left:4px solid var(--primary); padding:14px; border-radius:var(--radius-sm);">
          <div style="display:flex; justify-content:space-between; margin-bottom:6px;">
            <strong style="color:var(--text-main);">${escapeHtml(r.author)}</strong>
            <span style="color:#ffc107; font-weight:800;">${'★'.repeat(r.rating)}${'☆'.repeat(5 - r.rating)}</span>
          </div>
          <p style="margin:0; font-size:0.92rem; color:var(--text-muted);">${escapeHtml(r.comment)}</p>
        </div>
      `).join('') : '<p style="color:var(--text-muted);">Sé el primero en dejar una reseña sobre esta clínica.</p>'}
    </div>

    <div style="background:var(--bg-main); padding:20px; border-radius:var(--radius-md);">
      <h4 style="margin-bottom:12px;">Escribir una Reseña</h4>
      <form onsubmit="handleAddReview(event, ${clinic.id})">
        <div class="form-group">
          <label>Tu Nombre</label>
          <input type="text" id="reviewAuthor" class="form-control" placeholder="Ej: Camila Soto" required>
        </div>
        <div class="form-group">
          <label>Calificación (Estrellas)</label>
          <select id="reviewRating" class="form-control" required>
            <option value="5">⭐⭐⭐⭐⭐ 5 Estrellas (Excelente)</option>
            <option value="4">⭐⭐⭐⭐ 4 Estrellas (Muy Bueno)</option>
            <option value="3">⭐⭐⭐ 3 Estrellas (Aceptable)</option>
            <option value="2">⭐⭐ 2 Estrellas (Regular)</option>
            <option value="1">⭐ 1 Estrella (Malo)</option>
          </select>
        </div>
        <div class="form-group">
          <label>Comentario</label>
          <textarea id="reviewComment" class="form-control" placeholder="Cuéntanos tu experiencia con tu mascota..." required></textarea>
        </div>
        <button type="submit" class="btn btn-primary" style="width:100%; justify-content:center;">Publicar Opinión</button>
      </form>
    </div>
  `;

  modal.classList.add('open');
}

function closeClinicModal() {
  const modal = document.getElementById('clinicModal');
  if (modal) modal.classList.remove('open');
}

function handleAddReview(e, clinicId) {
  e.preventDefault();
  const author = document.getElementById('reviewAuthor').value.trim();
  const rating = parseInt(document.getElementById('reviewRating').value);
  const comment = document.getElementById('reviewComment').value.trim();

  const clinic = state.clinics.find(c => c.id === clinicId);
  if (clinic) {
    clinic.reviews = clinic.reviews || [];
    clinic.reviews.push({ id: Date.now(), author, rating, comment });
    state.saveClinics();
    showToast('¡Gracias! Tu reseña ha sido publicada.', 'success');
    openClinicModal(clinicId); // Reload modal
    renderClinics(); // Refresh cards
    renderHome();
  }
}

// 4. OFERTAS
function renderOffers() {
  const container = document.getElementById('offers-grid');
  if (!container) return;

  container.innerHTML = state.offers.map(offer => `
    <div class="card">
      <div class="card-body">
        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:12px;">
          <span style="background:var(--secondary-gradient); color:white; padding:6px 16px; border-radius:50px; font-weight:800; font-size:1rem;">-${offer.discount}% OFF</span>
          <span style="background:#e2e8f0; padding:4px 12px; border-radius:50px; font-size:0.85rem; font-weight:700;">Cupón Verificado</span>
        </div>
        <h3 class="card-title" style="font-size:1.4rem;">${escapeHtml(offer.title)}</h3>
        <p style="color:var(--text-muted); margin-bottom:16px;">${escapeHtml(offer.description)}</p>
        
        <div style="margin-bottom:20px; font-weight:700; color:var(--primary);">
          <i class="fas fa-hospital-alt"></i> ${escapeHtml(offer.clinicName)}
        </div>

        <div style="display:flex; gap:10px;">
          <button onclick="claimOffer('${offer.code}')" class="btn btn-primary" style="flex:1; justify-content:center;">
            <i class="fas fa-copy"></i> Copiar Código (${offer.code})
          </button>
        </div>
      </div>
    </div>
  `).join('');
}

function claimOffer(code) {
  navigator.clipboard?.writeText(code);
  showToast(`¡Código ${code} copiado al portapapeles! Preséntalo en la veterinaria.`, 'success');
}

// Fotos por defecto según especie
const DEFAULT_PET_PHOTOS = {
  Perro: [
    "https://images.unsplash.com/photo-1552053831-71594a27632d?q=80&w=800&auto=format&fit=crop",
    "https://images.unsplash.com/photo-1537151608828-ea2b11777ee8?q=80&w=800&auto=format&fit=crop",
    "https://images.unsplash.com/photo-1543466835-00a7907e9de1?q=80&w=800&auto=format&fit=crop"
  ],
  Gato: [
    "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?q=80&w=800&auto=format&fit=crop",
    "https://images.unsplash.com/photo-1573865526739-10659fec78a5?q=80&w=800&auto=format&fit=crop",
    "https://images.unsplash.com/photo-1533738363-b7f9aef128ce?q=80&w=800&auto=format&fit=crop"
  ],
  Exótico: [
    "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?q=80&w=800&auto=format&fit=crop"
  ]
};

// 5. MIS MASCOTAS (Pets CRUD)
function renderPets() {
  const container = document.getElementById('pets-grid');
  if (!container) return;

  if (state.pets.length === 0) {
    container.innerHTML = `
      <div style="grid-column:1/-1; text-align:center; padding:60px; background:white; border-radius:var(--radius-lg);">
        <i class="fas fa-paw fa-3x" style="color:#cbd5e1; margin-bottom:15px;"></i>
        <h3>No tienes mascotas registradas</h3>
        <p style="color:var(--text-muted);">Haz clic en "+ Registrar Mascota" para guardar la ficha médica de tus peludos.</p>
      </div>
    `;
    return;
  }

  container.innerHTML = state.pets.map(pet => {
    const photoUrl = pet.imageUrl || (DEFAULT_PET_PHOTOS[pet.species] ? DEFAULT_PET_PHOTOS[pet.species][0] : DEFAULT_PET_PHOTOS.Perro[0]);

    return `
      <div class="card">
        <div class="card-img-wrapper" style="height: 180px;">
          <img src="${photoUrl}" alt="${escapeHtml(pet.name)}" onerror="this.src='${DEFAULT_PET_PHOTOS.Perro[0]}'">
          <span class="badge-commune"><i class="fas fa-paw"></i> ${escapeHtml(pet.species)}</span>
        </div>
        <div class="card-body">
          <div style="display:flex; justify-content:space-between; align-items:flex-start; margin-bottom:12px;">
            <div>
              <h3 class="card-title" style="font-size:1.5rem; margin:0;">${escapeHtml(pet.name)}</h3>
              <span style="color:var(--primary); font-weight:700; font-size:0.9rem;">${escapeHtml(pet.breed)}</span>
            </div>
            <span style="font-size:1.8rem;">${pet.species === 'Perro' ? '🐶' : pet.species === 'Gato' ? '🐱' : '🐰'}</span>
          </div>

          <div style="display:flex; flex-direction:column; gap:8px; margin:16px 0; font-size:0.92rem;">
            <div><strong>Edad:</strong> ${pet.age} años</div>
            <div><strong>Microchip:</strong> ${escapeHtml(pet.chipNumber || 'Sin chip')}</div>
            <div><strong>Vacunación:</strong> ${pet.vaccinated ? '✅ Al día' : '⚠️ Pendiente'}</div>
            ${pet.notes ? `<div><strong>Notas Sanitarias:</strong> ${escapeHtml(pet.notes)}</div>` : ''}
          </div>

          <div class="card-footer">
            <button onclick="deletePet(${pet.id})" class="btn btn-secondary" style="color:var(--danger); padding:6px 14px; font-size:0.85rem;">
              <i class="fas fa-trash-alt"></i> Eliminar
            </button>
          </div>
        </div>
      </div>
    `;
  }).join('');
}

function handleAddPet(e) {
  e.preventDefault();
  const name = document.getElementById('petName').value.trim();
  const species = document.getElementById('petSpecies').value;
  const breed = document.getElementById('petBreed').value.trim();
  const age = parseInt(document.getElementById('petAge').value) || 0;
  const chipNumber = document.getElementById('petChip').value.trim();
  const vaccinated = document.getElementById('petVaccinated').checked;
  const notes = document.getElementById('petNotes').value.trim();
  const userPhotoUrl = document.getElementById('petPhotoUrl')?.value.trim();

  // Asignar foto ingresada o seleccionar una de la galería de Unsplash según especie
  const speciesPhotos = DEFAULT_PET_PHOTOS[species] || DEFAULT_PET_PHOTOS.Perro;
  const randomPhoto = speciesPhotos[Math.floor(Math.random() * speciesPhotos.length)];
  const imageUrl = userPhotoUrl || randomPhoto;

  state.pets.push({
    id: Date.now(),
    name, species, breed, age, chipNumber, vaccinated, notes, imageUrl
  });
  state.savePets();
  renderPets();
  closePetModal();
  showToast(`¡Mascota ${name} registrada con éxito!`, 'success');
}

function deletePet(id) {
  if (confirm('¿Deseas eliminar la ficha de esta mascota?')) {
    state.pets = state.pets.filter(p => p.id !== id);
    state.savePets();
    renderPets();
    showToast('Ficha de mascota eliminada.', 'success');
  }
}

function openPetModal() {
  document.getElementById('petModal').classList.add('open');
}
function closePetModal() {
  document.getElementById('petModal').classList.remove('open');
}

// 6. ADMINISTRACIÓN DE CLÍNICAS (Admin mode)
function handleAddClinicAdmin(e) {
  e.preventDefault();
  if (state.role !== 'ADMIN') {
    showToast('Solo los usuarios administradores pueden agregar clínicas.', 'error');
    return;
  }

  const name = document.getElementById('adminClinicName').value.trim();
  const commune = document.getElementById('adminClinicCommune').value.trim();
  const address = document.getElementById('adminClinicAddress').value.trim();
  const phone = document.getElementById('adminClinicPhone').value.trim();
  const hours = document.getElementById('adminClinicHours').value.trim();
  const emergency = document.getElementById('adminClinicEmergency').checked;
  const description = document.getElementById('adminClinicDesc').value.trim();

  const newClinic = {
    id: Date.now(),
    name, commune, address, phone,
    openingHours: hours,
    emergency247: emergency,
    description,
    imageUrl: "https://images.unsplash.com/photo-1530041539828-114de669390e?q=80",
    reviews: []
  };

  state.clinics.unshift(newClinic);
  state.saveClinics();
  renderClinics();
  populateCommunesFilter();
  renderHome();
  switchTab('clinicas');
  showToast(`¡Clínica "${name}" agregada con éxito!`, 'success');
}

// --- BÚSQUEDA RÁPIDA DE HERO ---
function setupHeroSearch() {
  const heroInput = document.getElementById('heroSearchInput');
  if (heroInput) {
    heroInput.addEventListener('keyup', (e) => {
      if (e.key === 'Enter') executeHeroSearch();
    });
  }
}

function executeHeroSearch() {
  const query = document.getElementById('heroSearchInput')?.value || '';
  switchTab('clinicas');
  const mainSearch = document.getElementById('clinicSearchInput');
  if (mainSearch) {
    mainSearch.value = query;
    renderClinics();
  }
}

// --- AUXILIARES Y ICONOS DE ESTRELLAS ---
function getStarIconsHTML(rating) {
  const fullStars = Math.floor(rating);
  const halfStar = rating % 1 >= 0.5;
  let html = '';
  for (let i = 0; i < fullStars; i++) html += '<i class="fas fa-star"></i>';
  if (halfStar) html += '<i class="fas fa-star-half-alt"></i>';
  const empty = 5 - Math.ceil(rating);
  for (let i = 0; i < empty; i++) html += '<i class="far fa-star"></i>';
  return html;
}

function escapeHtml(str) {
  if (!str) return '';
  return String(str)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#039;');
}

function showToast(message, type = 'success') {
  let container = document.querySelector('.toast-container');
  if (!container) {
    container = document.createElement('div');
    container.className = 'toast-container';
    document.body.appendChild(container);
  }

  const toast = document.createElement('div');
  toast.className = `toast toast-${type}`;
  toast.innerHTML = `
    <i class="fas ${type === 'success' ? 'fa-check-circle' : 'fa-exclamation-circle'}"></i>
    <span>${escapeHtml(message)}</span>
  `;

  container.appendChild(toast);
  setTimeout(() => {
    toast.style.opacity = '0';
    toast.style.transform = 'translateX(100%)';
    setTimeout(() => toast.remove(), 300);
  }, 3500);
}

// --- LOGICA DEL CARRUSEL DE HERO (SLIDER AUTOMÁTICO) ---
let currentHeroIndex = 0;
let heroTimer = null;

function setupHeroSlider() {
  startHeroTimer();
}

function startHeroTimer() {
  if (heroTimer) clearInterval(heroTimer);
  heroTimer = setInterval(() => {
    const slides = document.querySelectorAll('.hero-slide');
    if (slides.length > 0) {
      const nextIndex = (currentHeroIndex + 1) % slides.length;
      setHeroSlide(nextIndex);
    }
  }, 4500); // Cambia cada 4.5 segundos
}

function setHeroSlide(index) {
  const slides = document.querySelectorAll('.hero-slide');
  const dots = document.querySelectorAll('.hero-dot');

  if (slides.length === 0) return;

  slides.forEach((s, idx) => {
    if (idx === index) {
      s.classList.add('active');
    } else {
      s.classList.remove('active');
    }
  });

  dots.forEach((d, idx) => {
    if (idx === index) {
      d.classList.add('active');
    } else {
      d.classList.remove('active');
    }
  });

  currentHeroIndex = index;
  startHeroTimer(); // Reiniciar temporizador al hacer click manual
}
