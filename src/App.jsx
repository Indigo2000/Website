import * as React from 'react';
import './App.css'; // Import the CSS file
import stories from './stories';
import { FaGithub, FaLinkedin, FaFilePdf } from 'react-icons/fa';
import { SiCodesignal } from 'react-icons/si';

const welcome = {
  greeting: 'Will Irvine - Software Developer',
  title: 'MSc in Computer Science Coursework'
};

const useStorageState = (key, initialState) => {
  const [value, setValue] = React.useState(localStorage.getItem(key) || initialState);

  React.useEffect(() => {
    localStorage.setItem(key, value);
  }, [value, key]);

  return [value, setValue];
};

const App = () => {
  const [searchTerm, setSearchTerm] = useStorageState('search', '');

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  const filteredStories = stories
    .map((section) => ({
      ...section,
      items: section.items ? section.items.filter((story) =>
        story.title.toLowerCase().includes(searchTerm.toLowerCase())
      ) : [],
    }))
    .filter(section => section.items.length > 0);

  return (
    <div>
      <header>
        <h1>{welcome.greeting}</h1>
        <div className="social-links">
          <a href="/files/MyCV.pdf" download title="Download CV">
            <FaFilePdf />
          </a>
          <a href="https://github.com/Indigo2000" target="_blank" rel="noopener noreferrer" title="GitHub">
            <FaGithub />
          </a>
          <a href="https://www.linkedin.com/in/will-irvine-12675a236" target="_blank" rel="noopener noreferrer" title="LinkedIn">
            <FaLinkedin />
          </a>
          <a href="https://codesignal.com/learn/profile/cm5uvkicx001942mb7p3z8knt" target="_blank" rel="noopener noreferrer" title="CodeSignal">
            <SiCodesignal />
          </a>
        </div>
      </header>
      <hr />
      <h2>{welcome.title}</h2>
      <InputWithLabel id="search" value={searchTerm} isFocused onInputChange={handleSearch}>
        <strong>Search:</strong>
      </InputWithLabel>
      {Array.isArray(filteredStories) && <List sections={filteredStories} />}
    </div>
  );
};

const List = ({ sections }) => (
  <div>
    {sections.map((section, index) => (
      <div key={index}>
        <h2>{section.heading}</h2>
        {section.items && section.items.length > 0 ? (
          <ul>
            {section.items.map((item) => (
              <Item key={item.objectID} item={item} />
            ))}
          </ul>
        ) : (
          <p>No Matching Stories</p>
        )}
      </div>
    ))}
  </div>
);

const Item = ({ item }) => (
  <li>
    <span>
      <a href={item.url} target="_blank" rel="noopener noreferrer">{item.title}</a>
    </span>
  </li>
);

const InputWithLabel = ({ id, label, value, type = 'text', onInputChange, isFocused, children }) => {
  const inputRef = React.useRef();

  React.useEffect(() => {
    if (isFocused && inputRef.current) {
      inputRef.current.focus();
    }
  }, [isFocused]);

  return (
    <>
      <label htmlFor={id}>{children}{label}</label>
      &nbsp;
      <input ref={inputRef} id={id} type={type} value={value} onChange={onInputChange} />
    </>
  );
};

export default App;
